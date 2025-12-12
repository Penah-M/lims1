package com.example.order.ms.dto.response;

import com.example.order.ms.enums.OrderItemStatus;
import com.lims.common.enums.SampleType;
import com.lims.common.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderItemResponse {
     Long orderItemId;

     Long testId;
     String testName;
     Unit unit;
     BigDecimal price;

     String referenceRange;

     double maxValue;
     double minValue;

     SampleType sampleType;

     OrderItemStatus status;
}
