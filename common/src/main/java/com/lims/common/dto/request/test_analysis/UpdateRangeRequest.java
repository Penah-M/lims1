package com.lims.common.dto.request.test_analysis;

import com.lims.common.enums.Gender;
import com.lims.common.enums.TestStatus;
import com.lims.common.enums.Unit;
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
public class UpdateRangeRequest {
     Double minValue;
     Double maxValue;
     Gender gender;
     Integer ageMin;
     Integer ageMax;
     String pregnancyStatus;
     Unit unit;
     TestStatus status;
}
