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
import com.lims.common.exception.DefinitionNotFoundException;
import com.lims.common.exception.PatientNotFoundException;
import com.lims.common.exception.RangeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE,makeFinal = true)
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

        log.info("Yeni order yaradilir, patientId={}", request.getPatientId());

        PatientResponse patient = patientClient.findById(request.getPatientId());
        if (patient == null) {
            log.warn("Patient tapilmadi, id={}", request.getPatientId());
            throw new PatientNotFoundException("Patient tapilmadi");
        }

        OrderEntity order = new OrderEntity();
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setNotes(request.getNotes());
        order.setCreatedBy(5L);

        patientSnapshotMapper.mapPatientSnapshot(order, patient);
        orderRepository.save(order);

        int age = Period.between(patient.getBirthday(), LocalDate.now()).getYears();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderTestCreateRequest testReq : request.getTests()) {

            DefinitionResponse definition = definitionClient.findId(testReq.getTestDefinitionId());
            if (definition == null) {
                log.warn("Definition tapilmadi, id={}", testReq.getTestDefinitionId());
                throw new DefinitionNotFoundException("Test definition tapilmadi");
            }

            RangeResponse range = rangeClient.findBestRange(
                    definition.getId(),
                    patient.getGender(),
                    age,
                    request.getPregnancyStatus()
            );

            if (range == null) {
                log.warn("Range tapilmadi, definitionId={}", definition.getId());
                throw new RangeNotFoundException("Bu patient ucun uygun range tapilmadi");
            }

            OrderTestEntity orderTest =
                    orderTestMapper.toEntity(definition, range);

            orderTest.setOrder(order);
            orderTestRepository.save(orderTest);

            totalPrice = totalPrice.add(orderTest.getPrice());
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        log.info("Order yaradildi, orderId={}, totalPrice={}",
                order.getId(), order.getTotalPrice());

        // TODO [AUDIT-MS]: OrderCreatedEvent publish olunacaq

        return orderMapper.toCreateResponse(order);
    }

    /* =========================
       RECEIPT (REDIS)
       ========================= */
    @Override
    public ReceiptResponse getReceipt(Long orderId) {

        String key = RECEIPT_KEY + orderId;

        ReceiptResponse cached = cacheUtil.get(key);
        if (cached != null) {
            log.info("Receipt redis-den oxundu, orderId={}", orderId);
            return cached;
        }

        log.info("Receipt DB-den oxunur, orderId={}", orderId);

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order tapilmadi"));

        ReceiptResponse response = orderMapper.toReceiptResponse(order);
        cacheUtil.put(key, response, 15, ChronoUnit.MINUTES);

        log.info("Receipt redis-e elave olundu, orderId={}", orderId);
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

        // Order status
        order.setStatus(OrderStatus.CANCELLED);

        // Order test-lər
        if (order.getTests() != null) {
            for (OrderTestEntity test : order.getTests()) {
                test.setStatus(OrderTestStatus.CANCELED);
            }
        }

        orderRepository.save(order);

        // Redis invalidate
        cacheUtil.evict(RECEIPT_KEY + orderId);
        cacheUtil.evict(LAB_KEY + orderId);

        log.info("Order CANCELLED edildi ve redis cache temizlendi, orderId={}", orderId);

        // TODO [NOTIFICATION-MS]:
        // OrderCancelledEvent

        // TODO [AUDIT-MS]:
        // OrderCancelledEvent

    /* =========================
       LAB VIEW (REDIS)
       ========================= */}
    @Override
    public LabOrderResponse getLabOrder(Long orderId) {

        String key = LAB_KEY + orderId;

        LabOrderResponse cached = cacheUtil.get(key);
        if (cached != null) {
            log.info("Lab order redis-den oxundu, orderId={}", orderId);
            return cached;
        }

        log.info("Lab order DB-den oxunur, orderId={}", orderId);

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order tapilmadi"));

        LabOrderResponse response = orderMapper.toLabResponse(order);
        cacheUtil.put(key, response, 10, ChronoUnit.MINUTES);

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

    private String generateOrderNumber() {
        return "ORD-" + LocalDate.now()
                + "-" + System.currentTimeMillis();
    }

}
