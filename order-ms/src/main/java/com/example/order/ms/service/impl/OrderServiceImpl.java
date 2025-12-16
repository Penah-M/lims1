package com.example.order.ms.service.impl;

import com.example.order.ms.client.DefinitionClient;
import com.example.order.ms.client.PatientClient;
import com.example.order.ms.client.RangeClient;
import com.example.order.ms.dao.entity.OrderEntity;
import com.example.order.ms.dao.entity.OrderTestEntity;
import com.example.order.ms.dao.repository.OrderRepository;
import com.example.order.ms.dao.repository.OrderTestRepository;
import com.example.order.ms.dto.request.LabResultUpdateRequest;
import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.request.OrderTestCreateRequest;
import com.example.order.ms.dto.response.LabOrderResponse;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.ReceiptResponse;
import com.example.order.ms.enums.OrderStatus;
import com.example.order.ms.enums.OrderTestStatus;
import com.example.order.ms.exception.OrderAlreadyCanceledException;
import com.example.order.ms.exception.OrderNotFoundException;
import com.example.order.ms.exception.OrderTestNotFoundException;
import com.example.order.ms.mapper.OrderMapper;
import com.example.order.ms.mapper.OrderTestMapper;
import com.example.order.ms.mapper.PatientSnapshotMapper;
import com.example.order.ms.service.OrderService;
import com.example.order.ms.util.CacheUtil;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.exception.BusinessException;
import com.lims.common.exception.DefinitionNotFoundException;
import com.lims.common.exception.PatientNotFoundException;
import com.lims.common.exception.RangeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderTestRepository orderTestRepository;

    PatientClient patientClient;
    DefinitionClient definitionClient;
    RangeClient rangeClient;

    OrderMapper orderMapper;
    OrderTestMapper orderTestMapper;
    PatientSnapshotMapper patientSnapshotMapper;

    CacheUtil cacheUtil;

    private static final String RECEIPT_KEY = "order:receipt:";
    private static final String LAB_KEY = "order:lab:";


    @Override
    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        validateDiscount(request);

        PatientResponse patient = patientClient.findById(request.getPatientId());
        if (patient == null) {
            throw new PatientNotFoundException("Patient tapilmadi");
        }

        OrderEntity order = new OrderEntity();
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.CREATED);
        order.setNotes(request.getNotes());
        order.setCreatedBy(5L);

        // ❗ MÜTLƏQ başlanğıc dəyər
        order.setTotalPrice(BigDecimal.ZERO);
        order.setFinalPrice(BigDecimal.ZERO);

        order.setDiscountAmount(request.getDiscountAmount());
        order.setDiscountPercent(request.getDiscountPercent());
        order.setDiscountReason(request.getDiscountReason());

        patientSnapshotMapper.mapPatientSnapshot(order, patient);

        int age = Period.between(patient.getBirthday(), LocalDate.now()).getYears();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderTestCreateRequest testReq : request.getTests()) {

            DefinitionResponse definition =
                    definitionClient.findId(testReq.getTestDefinitionId());

            if (definition == null) {
                throw new DefinitionNotFoundException("Test definition tapilmadi");
            }

            RangeResponse range = rangeClient.findBestRange(
                    definition.getId(),
                    patient.getGender(),
                    age,
                    request.getPregnancyStatus()
            );

            if (range == null) {
                throw new RangeNotFoundException("Uygun range tapilmadi");
            }

            OrderTestEntity test = orderTestMapper.toEntity(definition, range);
            test.setOrder(order);

            order.getTests().add(test);

            totalPrice = totalPrice.add(test.getPrice());
        }

        // 🧮 TOTAL
        order.setTotalPrice(totalPrice);

        // 🎯 ENDIRIM
        BigDecimal finalPrice = totalPrice;

        if (order.getDiscountAmount() != null) {
            finalPrice = finalPrice.subtract(order.getDiscountAmount());
        }

        if (order.getDiscountPercent() != null) {
            BigDecimal percentDiscount =
                    totalPrice
                            .multiply(order.getDiscountPercent())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            finalPrice = finalPrice.subtract(percentDiscount);
        }

        order.setFinalPrice(finalPrice.max(BigDecimal.ZERO));


        // ✅ YALNIZ İNDİ SAVE
        orderRepository.save(order);

        return orderMapper.toCreateResponse(order);
    }

    @Override
    public ReceiptResponse getReceipt(Long orderId) {

        String cacheKey = RECEIPT_KEY + orderId;

        // 1️⃣ Cache yoxla
        ReceiptResponse cached = cacheUtil.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        // 2️⃣ DB-dən oxu
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order tapilmadi: " + orderId)
                );

        // 3️⃣ Response map et
        ReceiptResponse response = orderMapper.toReceiptResponse(order);

        // 4️⃣ Cache-ə yaz (TTL = 15 dəqiqə)
        cacheUtil.put(cacheKey, response, Duration.ofMinutes(15));

        return response;
    }


    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        log.info("Order cancel edilmek istenilir, orderId={}", orderId);

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order tapilmadi"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.warn("Order artiq CANCELLED-dir, orderId={}", orderId);
            throw new OrderAlreadyCanceledException("Order artiq legv edilib");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            log.warn("COMPLETED order cancel edile bilmez, orderId={}", orderId);
            throw new OrderAlreadyCanceledException("Tamamlanmis order legv edile bilmez");
        }

        order.setStatus(OrderStatus.CANCELLED);

        if (order.getTests() != null) {
            for (OrderTestEntity test : order.getTests()) {
                test.setStatus(OrderTestStatus.CANCELED);
            }
        }

        orderRepository.save(order);

        cacheUtil.evict(RECEIPT_KEY + orderId);
        cacheUtil.evict(LAB_KEY + orderId);

        log.info("Order CANCELLED edildi ve redis cache temizlendi, orderId={}", orderId);

        // TODO [NOTIFICATION-MS]:
        // OrderCancelledEvent

        // TODO [AUDIT-MS]:
        // OrderCancelledEvent

    /* =========================
       LAB VIEW (REDIS)
       ========================= */
    }

    @Override
    public LabOrderResponse getLabOrder(Long orderId) {

        String cacheKey = LAB_KEY + orderId;

        LabOrderResponse cached = cacheUtil.get(cacheKey);
        if (cached != null) {
            log.info("Lab order redis-den oxundu, orderId={}", orderId);
            return cached;
        }

        log.info("Lab order DB-den oxunur, orderId={}", orderId);

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order tapilmadi"));

        LabOrderResponse response = orderMapper.toLabResponse(order);
        cacheUtil.put(cacheKey, response, Duration.ofMinutes(10));

        log.info("Lab order redis-e elave olundu, orderId={}", orderId);
        return response;
    }

    /* =========================
       UPDATE TEST RESULT
       ========================= */
    @Override
    @Transactional
    public void updateTestResult(Long orderTestId, LabResultUpdateRequest request) {

        log.info("Lab neticesi yenilenir, orderTestId={}", orderTestId);

        OrderTestEntity test = orderTestRepository.findById(orderTestId)
                .orElseThrow(() -> new OrderTestNotFoundException("Order test tapilmadi"));

        if (test.getOrder().getStatus() == OrderStatus.CANCELLED) {
            throw new OrderAlreadyCanceledException("Canceled order uzre emeliyyat olmaz");
        }

        test.setResult(request.getResult());
        test.setStatus(OrderTestStatus.READY);
        orderTestRepository.save(test);

        Long orderId = test.getOrder().getId();

        cacheUtil.evict(RECEIPT_KEY + orderId);
        cacheUtil.evict(LAB_KEY + orderId);

        log.info("Redis cache temizlendi, orderId={}", orderId);

        boolean allReady = test.getOrder().getTests()
                .stream()
                .allMatch(t -> t.getStatus() == OrderTestStatus.READY);

        if (allReady) {
            test.getOrder().setStatus(OrderStatus.COMPLETED);
            orderRepository.save(test.getOrder());

            log.info("Order COMPLETED oldu, orderId={}", orderId);

            // TODO [NOTIFICATION-MS]: OrderCompletedEvent
            // TODO [AUDIT-MS]: OrderCompletedEvent
        }
    }

    @Transactional
    public void deleteOrder(Long orderId) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order tapilmadi"));
        orderRepository.delete(order);

        cacheUtil.evict("order:receipt:" + orderId);
        cacheUtil.evict("order:lab:" + orderId);
    }


    private String generateOrderNumber() {
        return "ORD-" + LocalDate.now()
                + "-" + System.currentTimeMillis();
    }

    private void validateDiscount(OrderCreateRequest request) {

        if (request.getDiscountPercent() != null &&
                request.getDiscountAmount() != null) {

            throw new BusinessException(
                    "Endirim hem faiz, hem mebleg ola bilmez"
            ) {
            };
        }

        if (request.getDiscountPercent() != null &&
                (request.getDiscountPercent().compareTo(BigDecimal.ZERO) < 0 ||
                        request.getDiscountPercent().compareTo(BigDecimal.valueOf(100)) > 0)) {

            throw new BusinessException(
                    "Endirim faizi 0–100 arasi olmalidir"
            ) {
            };
        }

        if (request.getDiscountAmount() != null &&
                request.getDiscountAmount().compareTo(BigDecimal.ZERO) < 0) {

            throw new BusinessException(
                    "Endirim meblegi menfi ola bilmez"
            ) {
            };
        }
    }


}
