package com.lims.common.exception;

public class AlreadyExistsException extends RuntimeException
{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
