package com.example.order.ms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderListResponse {
    private Long orderId;
    private String orderNumber;
    private String status;
    private String createdAt;

    private Double totalPrice;

    private String patientName;
    private String patientGender;

    // optional
    private Integer testCount;
}
