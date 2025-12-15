package com.example.order.ms.dto.response;

import com.example.order.ms.enums.OrderTestStatus;
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
public class LabTestResponse {

     Long orderTestId;

     String testName;
     Unit unit;

     BigDecimal minValue;
     BigDecimal maxValue;

     OrderTestStatus status;

    // TEMP – Result MS gelene kimi
     String result;
}
