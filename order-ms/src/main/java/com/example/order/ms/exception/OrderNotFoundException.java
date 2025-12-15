package com.example.order.ms.exception;

import com.lims.common.exception.BusinessException;

public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
