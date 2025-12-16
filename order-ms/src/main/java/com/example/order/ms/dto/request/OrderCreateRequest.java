package com.example.order.ms.dto.request;

import com.lims.common.enums.PregnancyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)

public class OrderCreateRequest {

      Long patientId;

      PregnancyStatus pregnancyStatus;

      List<OrderTestCreateRequest> tests;

      BigDecimal discountPercent;   // optional
      BigDecimal discountAmount;    // optional
      String discountReason;


      String notes;
}
