package com.example.order.ms.domain.discount;

import com.example.order.ms.dto.request.OrderAddTestRequest;
import com.example.order.ms.dto.request.OrderCreateRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class DiscountInfo {

    private final BigDecimal percent;
    private final BigDecimal amount;
    private final String reason;

    public static DiscountInfo from(OrderCreateRequest r) {
        return new DiscountInfo(
                r.getDiscountPercent(),
                r.getDiscountAmount(),
                r.getDiscountReason()
        );
    }

    public static DiscountInfo from(OrderAddTestRequest r) {
        return new DiscountInfo(
                r.getDiscountPercent(),
                r.getDiscountAmount(),
                r.getDiscountReason()
        );
    }
}

