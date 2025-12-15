package com.example.order.ms.exception;

import com.lims.common.exception.BusinessException;

public class OrderAlreadyCanceledException extends BusinessException {
    public OrderAlreadyCanceledException(String message) {
        super(message);
    }
}
