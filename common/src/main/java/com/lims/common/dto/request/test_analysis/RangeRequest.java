package com.lims.common.dto.request.test_analysis;


import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
import com.lims.common.enums.Unit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class RangeRequest {
//     @NotNull(message = "Test definition is required")
//     Long testDefinitionId;

     @NotNull(message = "Min value is required")
     Double minValue;

     @NotNull(message = "Max value is required")
     Double maxValue;

     @NotNull(message = "Gender is required")
     Gender gender;

     @NotNull(message = "Minimum age is required")
     @Min(value = 0, message = "AgeMin cannot be negative")
     Integer ageMin;

     @NotNull(message = "Maximum age is required")
     @Min(value = 0, message = "AgeMax cannot be negative")
     Integer ageMax;

     PregnancyStatus pregnancyStatus;

     Unit unit;
}
