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
public class OrderCreateResponse {

     Long orderId;
     String orderNumber;
     String status;
     Double totalPrice;
     String createdAt;

     PatientResponse patient;
     List<OrderItemResponse> items;
}
