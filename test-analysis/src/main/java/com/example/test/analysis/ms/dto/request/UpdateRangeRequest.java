package com.example.test.analysis.ms.dto.request;

import com.example.test.analysis.ms.enums.Gender;
import com.example.test.analysis.ms.enums.PregnancyStatus;
import com.example.test.analysis.ms.enums.TestStatus;
import com.example.test.analysis.ms.enums.Unit;
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
     PregnancyStatus pregnancyStatus;
     Unit unit;
     TestStatus status;
}
