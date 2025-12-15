package com.example.order.ms.dto.response;

import com.example.order.ms.enums.OrderStatus;
import com.lims.common.dto.response.patient.PatientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ReceiptItemResponse {

     String testName;
     BigDecimal price;
}