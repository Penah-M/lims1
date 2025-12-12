package com.example.order.ms.service;

import com.example.order.ms.client.DefinitionClient;
import com.example.order.ms.client.PatientClient;
import com.example.order.ms.client.RangeClient;
import com.example.order.ms.dao.entity.OrderEntity;
import com.example.order.ms.dao.entity.OrderTestEntity;
import com.example.order.ms.dao.repository.OrderRepository;
import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.request.OrderTestRequest;
import com.example.order.ms.dto.response.BillingResponse;
import com.example.order.ms.dto.response.LaboratoryResponse;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.OrderCreateResponse1;
import com.example.order.ms.dto.response.OrderItemResponse;
import com.example.order.ms.enums.OrderItemStatus;
import com.example.order.ms.enums.OrderStatus;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.enums.PregnancyStatus;
import com.lims.common.exception.DefinitionNotFoundException;
import com.lims.common.exception.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderImpl {

    OrderRepository orderRepository;
    PatientClient patientClient;
    RangeClient rangeClient;
    DefinitionClient definitionClient;


    public OrderCreateResponse createResponse(OrderCreateRequest request) {
        PatientResponse patient = patientClient.findById(request.getPatientId());
        if (patient == null)
            throw new PatientNotFoundException("Patient tapilmadi");
        int age = calculateAge(patient.getBirthday());

        List<OrderTestEntity> orderTests = new ArrayList<>();
        OrderEntity order = new OrderEntity();
        order.setPatientId(request.getPatientId());
        order.setNotes(request.getNote());
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderTestRequest testReq : request.getTests()) {

            Long definitionId = testReq.getTestId();

            // 3.1 TestDefinition məlumatı (name, unit, price və s.)
            DefinitionResponse test = definitionClient.findId(definitionId);

            if (test == null)
                throw new DefinitionNotFoundException("Test tapilmadi");

            RangeResponse bestRange = rangeClient.findBestRange(definitionId,
                    patient.getGender(),
                    age,
                    PregnancyStatus.NOT_PREGNANT);

            OrderTestEntity orderTest = new OrderTestEntity();
            orderTest.setOrder(order);
            orderTest.setTestId(definitionId);
            orderTest.setUnit(test.getUnit());
            orderTest.setStatus("PENDING");
            orderTest.setPrice(test.getPrice());
            orderTest.setMaxValue(bestRange.getMaxValue());
            orderTest.setMinValue(bestRange.getMinValue());
            orderTest.setOrderTestDetails(new ArrayList<>());

            orderTests.add(orderTest);
            totalPrice = totalPrice.add(orderTest.getPrice());

        }
        order.setOrderTestEntities(orderTests);

        orderRepository.save(order);

        return OrderCreateResponse.builder()
                .createdAt("now")
                .orderId(order.getId())
                .status("Pending")
                .patient(patient)
                .totalPrice(totalPrice)
                .build();
    }

    public OrderCreateResponse1 createOrder(OrderCreateRequest request) {
        // Patient məlumatını çəkmək
        PatientResponse patient = patientClient.findById(request.getPatientId());
        if (patient == null) throw new PatientNotFoundException("Patient tapilmadi");

        int age = calculateAge(patient.getBirthday());
        String orderNumber = generateOrderNumber();
        LocalDateTime createdAt = LocalDateTime.now();

        List<OrderItemResponse> billingItems = new ArrayList<>();
        List<OrderItemResponse> laboratoryItems = new ArrayList<>();
        BigDecimal totalPrice1 = BigDecimal.ZERO;

        // Order entity yaradılır və save olunur
        OrderEntity order = new OrderEntity();
        order.setPatientId(request.getPatientId());
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        Long orderId = order.getId();
        OrderStatus status = order.getStatus();

        // Tests loop
        for (OrderTestRequest testReq : request.getTests()) {
            DefinitionResponse test = definitionClient.findId(testReq.getTestId());
            if (test == null) throw new DefinitionNotFoundException("Test tapilmadi");

            RangeResponse bestRange = rangeClient.findBestRange(
                    testReq.getTestId(),
                    patient.getGender(),
                    age,
                    PregnancyStatus.NOT_PREGNANT
            );

            // Billing üçün: qiymət daxil
            if (request.isGenerateBilling()) {
                OrderItemResponse billingItem = OrderItemResponse.builder()
                        .testId(test.getId())
                        .testName(test.getName())
                        .unit(test.getUnit())
                        .price(test.getPrice())
                        .minValue(bestRange.getMinValue())
                        .maxValue(bestRange.getMaxValue())
                        .status(OrderItemStatus.PENDING)
                        .build();
                billingItems.add(billingItem);
                totalPrice1 = totalPrice1.add(test.getPrice());
            }

            // Laboratory üçün: qiymət olmadan
            if (request.isGenerateLaboratory()) {
                OrderItemResponse labItem = OrderItemResponse.builder()
                        .testId(test.getId())
                        .testName(test.getName())
                        .unit(test.getUnit())
                        .minValue(bestRange.getMinValue())
                        .maxValue(bestRange.getMaxValue())
                        .status(OrderItemStatus.PENDING)
                        .build();
                laboratoryItems.add(labItem);
            }
        }

        // BillingResponse yaradılır
        BillingResponse billingResponse = null;
        if (request.isGenerateBilling()) {
            billingResponse = BillingResponse.builder()
                    .orderId(orderId)
                    .orderNumber(orderNumber)
                    .status(status)
                    .patient(patient)
                    .createdAt(createdAt)
                    .totalPrice(totalPrice1)
                    .items(billingItems)
                    .build();
        }

        // LaboratoryResponse yaradılır
        LaboratoryResponse laboratoryResponse = null;
        if (request.isGenerateLaboratory()) {
            laboratoryResponse = LaboratoryResponse.builder()
                    .orderId(orderId)
                    .orderNumber(orderNumber)
                    .status(status)
                    .patient(patient)
                    .createdAt(createdAt)
                    .items(laboratoryItems)
                    .build();
        }

        // Son response
        return OrderCreateResponse1.builder()
                .billingResponse(billingResponse)
                .laboratoryResponse(laboratoryResponse)
                .build();
    }



    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Patient doğum tarixi mövcud deyil");
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private String generateOrderNumber() {
        AtomicLong orderCounter = new AtomicLong(1000);
        long seq = orderCounter.incrementAndGet();
        return "ORD-" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "-" + seq;
    }


}
