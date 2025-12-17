package com.example.order.ms.domain.discount;

import com.lims.common.exception.BusinessException;

import java.math.BigDecimal;

public class DiscountValidator {

    public static void validate(DiscountInfo discount) {

        if (discount.getPercent() != null &&
                discount.getAmount() != null) {
            throw new BusinessException("Endirim hem faiz, hem mebleg ola bilmez") {
            };
        }

        if (discount.getPercent() != null &&
                (discount.getPercent().compareTo(BigDecimal.ZERO) < 0 ||
                        discount.getPercent().compareTo(BigDecimal.valueOf(100)) > 0)) {
            throw new BusinessException("Endirim faizi 0–100 arasi olmalidir") {
            };
        }

        if (discount.getAmount() != null &&
                discount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Endirim meblegi menfi ola bilmez") {

            };
        }
    }
}
