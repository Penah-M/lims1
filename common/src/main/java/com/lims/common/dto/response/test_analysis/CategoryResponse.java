package com.lims.common.dto.response.test_analysis;


import com.lims.common.enums.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String code;
    TestStatus status;
    LocalDateTime createdAt;
    LocalDateTime updateAt;

}
