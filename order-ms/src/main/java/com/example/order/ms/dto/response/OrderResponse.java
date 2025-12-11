package com.example.order.ms.dto.response;

import com.lims.common.dto.response.patient.PatientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderResponse {
    private Long orderId;
    private String orderNumber;
    private String status;

    private String createdAt;
    private String updatedAt;

    private PatientResponse patient;

    private Long doctorId;
    private String note;

    private List<OrderItemResponse> items;

    private Double totalPrice;

}
