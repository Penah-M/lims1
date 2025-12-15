package com.example.order.ms.exception;

import com.lims.common.exception.BusinessException;

public class OrderTestNotFoundException extends BusinessException {

    public OrderTestNotFoundException(String message) {
        super(message);
    }
}