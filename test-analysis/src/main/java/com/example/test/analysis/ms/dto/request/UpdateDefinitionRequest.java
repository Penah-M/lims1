package com.example.test.analysis.ms.dto.request;

import com.example.test.analysis.ms.enums.SampleType;
import com.example.test.analysis.ms.enums.TestStatus;

import com.example.test.analysis.ms.enums.Unit;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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
public class UpdateDefinitionRequest {
    String codeShort;
    String codeLong;
    String name;
    String description;
    @JsonSetter(contentNulls = Nulls.SKIP)
    Unit unit;
    SampleType sampleType;
    Integer turnaroundTime;
    Double price;
    Long categoryId;
    TestStatus status;
}
