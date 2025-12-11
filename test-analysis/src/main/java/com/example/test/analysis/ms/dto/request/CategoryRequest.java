package com.example.test.analysis.ms.dto.request;


import jakarta.validation.constraints.NotBlank;
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
public class CategoryRequest {

    @NotBlank(message = "Name cannot be blank")
    String name;

    @NotBlank(message = "Code cannot be blank")
    String code;

}
