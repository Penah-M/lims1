package com.lims.common.dto.response.test_analysis;


import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
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
