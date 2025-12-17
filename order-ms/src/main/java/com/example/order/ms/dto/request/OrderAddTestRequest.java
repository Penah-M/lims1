package com.example.order.ms.dto.request;

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

public class OrderAddTestRequest  {
    List<OrderTestCreateRequest> tests;
    String notes;
    String discountReason;
     BigDecimal discountPercent;
     BigDecimal discountAmount;

}
