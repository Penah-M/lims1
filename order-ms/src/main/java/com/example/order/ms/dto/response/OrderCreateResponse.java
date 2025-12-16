package com.example.order.ms.dto.response;

import com.example.order.ms.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderCreateResponse {

     Long orderId;
     String orderNumber;
     OrderStatus status;
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     LocalDateTime createdAt;
     BigDecimal totalPrice;
}