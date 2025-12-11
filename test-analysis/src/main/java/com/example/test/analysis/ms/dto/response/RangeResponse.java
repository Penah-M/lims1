package com.example.test.analysis.ms.dto.response;

import com.example.test.analysis.ms.enums.Gender;
import com.example.test.analysis.ms.enums.PregnancyStatus;
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
public class RangeResponse {

    Long id;
    Long testDefinitionId;
    Double minValue;
    Double maxValue;
    Gender gender;
    Integer ageMin;
    Integer ageMax;
    PregnancyStatus pregnancyStatus;
    Unit unit;
    TestStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
