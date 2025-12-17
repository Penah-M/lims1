package com.example.order.ms.service.impl;

import com.example.order.ms.client.DefinitionClient;
import com.example.order.ms.client.PatientClient;
import com.example.order.ms.client.RangeClient;
import com.example.order.ms.dao.entity.OrderEntity;
import com.example.order.ms.dao.entity.OrderTestEntity;
import com.example.order.ms.dao.repository.OrderRepository;
import com.example.order.ms.dao.repository.OrderTestRepository;
import com.example.order.ms.domain.discount.DiscountInfo;
import com.example.order.ms.domain.factory.OrderTestCreator;
import com.example.order.ms.domain.pricing.OrderPricingService;
import com.example.order.ms.dto.request.LabResultUpdateRequest;
import com.example.order.ms.dto.request.OrderAddTestRequest;
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
import com.example.order.ms.service.OrderService;
import com.example.order.ms.util.CacheUtil;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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

    OrderTestCreator   testCreator;
    OrderPricingService pricingService;

    CacheUtil cacheUtil;

    private static final String RECEIPT_KEY = "order:receipt:";
    private static final String LAB_KEY = "order:lab:";

    @Override
    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        DiscountInfo discount = DiscountInfo.from(request);
        pricingService.validateDiscount(discount.getPercent(), discount.getAmount());


        PatientResponse patient = patientClient.findById(request.getPatientId());
        if (patient == null) {
            throw new PatientNotFoundException("Patient tapilmadi");
        }

        OrderEntity order = new OrderEntity();
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.CREATED);
        order.setNotes(request.getNotes());
        order.setCreatedBy(5L);


        order.setPatientId(patient.getId());
        order.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
        order.setPatientGender(patient.getGender());
        order.setPatientBirthDate(patient.getBirthday());


        order.setDiscountPercent(discount.getPercent());
        order.setDiscountAmount(discount.getAmount());
        order.setDiscountReason(discount.getReason());


        int age = Period.between(patient.getBirthday(), LocalDate.now()).getYears();
        BigDecimal total = addTestsInternal(
                order,
                request.getTests(),
                patient.getGender(),
                age,
                request.getPregnancyStatus()
        );

        order.setTotalPrice(total);

        BigDecimal finalPrice = pricingService.calculateFinalPrice(
                total,
                order.getDiscountPercent(),
                order.getDiscountAmount()
        );
        order.setFinalPrice(finalPrice);


        orderRepository.save(order);

        return orderMapper.toCreateResponse(order);
    }


    @Override
    @Transactional
    public ReceiptResponse addTestsToOrder(Long orderId, OrderAddTestRequest request) {
        OrderEntity order = findOrder(orderId);

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessException("Bu status-da order-e yeni test elave etmek olmaz") {
            };
        }

        if (request.getNotes() != null && !request.getNotes().isBlank()) {

            String old = order.getNotes() == null ? "" : order.getNotes().trim();
            String add = request.getNotes().trim();
            order.setNotes(old.isEmpty() ? add : (old + "\n" + add));
        }

        BigDecimal newPercent = request.getDiscountPercent() != null ? request.getDiscountPercent() : order.getDiscountPercent();
        BigDecimal newAmount  = request.getDiscountAmount()  != null ? request.getDiscountAmount()  : order.getDiscountAmount();
        String newReason      = request.getDiscountReason()  != null ? request.getDiscountReason()  : order.getDiscountReason();

        pricingService.validateDiscount(newPercent, newAmount);

        order.setDiscountPercent(newPercent);
        order.setDiscountAmount(newAmount);
        order.setDiscountReason(newReason);

        int age = Period.between(order.getPatientBirthDate(), LocalDate.now()).getYears();

        BigDecimal added = addTestsInternal(
                order,
                request.getTests(),
                order.getPatientGender(),
                age,
                PregnancyStatus.NOT_PREGNANT
        );

        BigDecimal newTotal = order.getTotalPrice().add(added);
        order.setTotalPrice(newTotal);

        BigDecimal finalPrice = pricingService.calculateFinalPrice(
                newTotal,
                order.getDiscountPercent(),
                order.getDiscountAmount()
        );
        order.setFinalPrice(finalPrice);

        orderRepository.save(order);
        cacheUtil.evict("order:receipt:" + orderId);
        cacheUtil.evict("order:lab:" + orderId);

        return orderMapper.toReceiptResponse(order);
    }

    private BigDecimal addTestsInternal(
            OrderEntity order,
            List<OrderTestCreateRequest> tests,
            Gender gender,
            int age,
            PregnancyStatus pregnancyStatus
    ) {
        if (tests == null || tests.isEmpty()) {
            throw new BusinessException("Tests bos ola bilmez") {
            };
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderTestCreateRequest t : tests) {

            DefinitionResponse definition = definitionClient.findId(t.getTestDefinitionId());
            if (definition == null) {
                throw new DefinitionNotFoundException("Test definition tapilmadi");
            }

            RangeResponse range = rangeClient.findBestRange(
                    definition.getId(),
                    gender,
                    age,
                    pregnancyStatus
            );

            if (range == null) {
                throw new RangeNotFoundException("Uygun range tapilmadi");
            }

            OrderTestEntity test = testCreator.create(definition, range);
            order.addTest(test);
            total = total.add(test.getPrice());
        }

        return total;
    }


    @Override
    public ReceiptResponse getReceipt(Long orderId) {

        String cacheKey = RECEIPT_KEY + orderId;

        ReceiptResponse cached = cacheUtil.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        OrderEntity order = findOrder(orderId);

        ReceiptResponse response = orderMapper.toReceiptResponse(order);

        cacheUtil.put(cacheKey, response, Duration.ofMinutes(15));
        return response;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        log.info("Order cancel edilmek istenilir, orderId={}", orderId);

        OrderEntity order = findOrder(orderId);

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

        OrderEntity order = findOrder(orderId);;

        LabOrderResponse response = orderMapper.toLabResponse(order);
        cacheUtil.put(cacheKey, response, Duration.ofMinutes(10));

        log.info("Lab order redis-e elave olundu, orderId={}", orderId);
        return response;
    }

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
        OrderEntity order = findOrder(orderId);
        orderRepository.delete(order);

        cacheUtil.evict("order:receipt:" + orderId);
        cacheUtil.evict("order:lab:" + orderId);
    }

    private OrderEntity findOrder(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order tapilmadi"));
    }

    private String generateOrderNumber() {
        return "ORD-" + LocalDate.now()
                + "-" + System.currentTimeMillis();
    }

}
