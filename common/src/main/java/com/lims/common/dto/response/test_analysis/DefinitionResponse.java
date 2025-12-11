package com.lims.common.dto.response.test_analysis;


import com.lims.common.enums.SampleType;
import com.lims.common.enums.TestStatus;
import com.lims.common.enums.Unit;
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
