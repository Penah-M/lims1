package com.example.test.analysis.ms.dto.response;

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
