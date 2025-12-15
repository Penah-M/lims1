package com.example.order.ms.mapper;

import com.example.order.ms.dao.entity.OrderEntity;
import com.example.order.ms.dao.entity.OrderTestEntity;
import com.example.order.ms.dto.response.LabOrderResponse;
import com.example.order.ms.dto.response.LabTestResponse;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.PatientSnapshotResponse;
import com.example.order.ms.dto.response.ReceiptItemResponse;
import com.example.order.ms.dto.response.ReceiptResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderCreateResponse toCreateResponse(OrderEntity order) {

        if (order == null) {
            throw new IllegalArgumentException("Order null ola bilmez");
        }

        return OrderCreateResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public ReceiptResponse toReceiptResponse(OrderEntity order) {

        List<ReceiptItemResponse> items = new ArrayList<>();

        if (order.getTests() != null) {
            for (OrderTestEntity test : order.getTests()) {

                ReceiptItemResponse item = ReceiptItemResponse.builder()
                        .testName(test.getTestName())
                        .price(test.getPrice())
                        .build();

                items.add(item);
            }
        }

        return ReceiptResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .patientFullName(order.getPatientFullName())
                .items(items)
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public LabOrderResponse toLabResponse(OrderEntity order) {

        List<LabTestResponse> labTests = new ArrayList<>();

        if (order.getTests() != null) {
            for (OrderTestEntity test : order.getTests()) {

                LabTestResponse labTest = LabTestResponse.builder()
                        .orderTestId(test.getId())
                        .testName(test.getTestName())
                        .unit(test.getUnit())
                        .minValue(test.getMinValue())
                        .maxValue(test.getMaxValue())
                        .status(test.getStatus())
                        .result(test.getResult())           // TEMP: Result MS
                        .build();

                labTests.add(labTest);
            }
        }

        Integer age = null;
        if (order.getPatientBirthDate() != null) {
            age = Period.between(
                    order.getPatientBirthDate(),
                    LocalDate.now()
            ).getYears();
        }

        return LabOrderResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .patient(
                        PatientSnapshotResponse.builder()
                                .fullName(order.getPatientFullName())
                                .gender(order.getPatientGender())
                                .age(age)
                                .build()
                )
                .tests(labTests)
                .build();
    }

    /*
     * TODO [RESULT-MS]:
     * Result MS yazildiqdan sonra:
     * - test.getResult() buradan silinecek
     * - nəticələr Result MS-dən oxunacaq
     */
}
