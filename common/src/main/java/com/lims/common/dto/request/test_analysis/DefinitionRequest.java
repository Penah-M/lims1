package com.lims.common.dto.request.test_analysis;


import com.lims.common.enums.SampleType;
import com.lims.common.enums.Unit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class DefinitionRequest {

    @NotBlank(message = "CodeShort cannot be blank")
    String codeShort;

    @NotBlank(message = "CodeLong cannot be blank")
    String codeLong;

    @NotBlank(message = "Name cannot be blank")
    String name;

    String description;

    @NotNull(message = "Unit cannot be null")
    Unit unit;

    SampleType sampleType;

    @NotNull(message = "Turnaround time is required")
    @Min(value = 1, message = "TAT must be at least 1 hour")
    Integer turnaroundTime;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    Double price;

    @NotNull(message = "Category ID is required")
    Long categoryId;
}
