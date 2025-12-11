package com.lims.common.dto.response.exceptionr;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
     LocalDateTime timestamp;
     Integer status;
     String error;
     String message;
     String path;
//    Map<String, String> validation;

}
