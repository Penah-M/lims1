package com.example.order.ms.domain.pricing;

import com.lims.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class OrderPricingService {

    public void validateDiscount(
            BigDecimal percent,
            BigDecimal amount
    ) {
        if (percent != null && amount != null) {
            throw new BusinessException("Endirim hem faiz, hem mebleg ola bilmez") {
            };
        }

        if (percent != null &&
                (percent.compareTo(BigDecimal.ZERO) < 0 ||
                        percent.compareTo(BigDecimal.valueOf(100)) > 0)) {
            throw new BusinessException("Endirim faizi 0–100 arasi olmalidir") {
            };
        }

        if (amount != null && amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Endirim menfi ola bilmez") {
            };
        }
    }

    public BigDecimal calculateFinalPrice(
            BigDecimal totalPrice,
            BigDecimal percent,
            BigDecimal amount
    ) {
        BigDecimal finalPrice = totalPrice;

        if (amount != null) {
            finalPrice = finalPrice.subtract(amount);
        }

        if (percent != null) {
            BigDecimal percentDiscount =
                    totalPrice
                            .multiply(percent)
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            finalPrice = finalPrice.subtract(percentDiscount);
        }

        return finalPrice.max(BigDecimal.ZERO);
    }
}
