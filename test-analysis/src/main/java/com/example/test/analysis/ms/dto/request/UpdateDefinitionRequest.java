package com.example.test.analysis.ms.dto.request;


import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.lims.common.enums.SampleType;
import com.lims.common.enums.TestStatus;
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
public class UpdateDefinitionRequest {
    String codeShort;
    String codeLong;
    String name;
    String description;
    @JsonSetter(contentNulls = Nulls.SKIP)
    Unit unit;
    SampleType sampleType;
    Integer turnaroundTime;
    BigDecimal price;
    Long categoryId;
    TestStatus status;
}
