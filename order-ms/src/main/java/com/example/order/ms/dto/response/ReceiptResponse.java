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
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ReceiptResponse {

    Long orderId;
    String orderNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    OrderStatus status;

    String patientFullName;

    List<ReceiptItemResponse> items;

    BigDecimal totalPrice;
    BigDecimal discountAmount;

    BigDecimal discountPercent;

    BigDecimal finalPrice;


}