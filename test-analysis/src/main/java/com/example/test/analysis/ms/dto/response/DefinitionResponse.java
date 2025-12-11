package com.example.test.analysis.ms.dto.response;

import com.example.test.analysis.ms.enums.SampleType;
import com.example.test.analysis.ms.enums.TestStatus;
import com.example.test.analysis.ms.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class DefinitionResponse {
    Long id;
    String codeShort;
    String codeLong;
    String name;
    String description;
    Unit unit;
    SampleType sampleType;
    Integer turnaroundTime;
    Double price;
    Long categoryId;
    TestStatus status;
    String categoryName;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
}
