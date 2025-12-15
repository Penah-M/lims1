package com.example.order.ms.exception;

import com.lims.common.exception.BusinessException;

public class InvalidOrderStateException extends BusinessException {
    public InvalidOrderStateException(String message) {
        super(message);
    }
}
