package com.example.order.ms.exception;

import com.lims.common.dto.response.exceptionr.ErrorResponse;
import com.lims.common.exception.BusinessException;
import com.lims.common.exception.DefinitionNotFoundException;
import com.lims.common.exception.PatientNotFoundException;
import com.lims.common.exception.RangeNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Order not found: {}", ex.getMessage());

        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(OrderTestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderTestNotFound(
            OrderTestNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Order test not found: {}", ex.getMessage());

        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(OrderAlreadyCanceledException.class)
    public ResponseEntity<ErrorResponse> handleOrderAlreadyCanceled(
            OrderAlreadyCanceledException ex,
            HttpServletRequest request) {

        log.warn("Order already canceled: {}", ex.getMessage());

        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrderState(
            InvalidOrderStateException ex,
            HttpServletRequest request) {

        log.warn("Invalid order state: {}", ex.getMessage());

        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }


    @ExceptionHandler({
            PatientNotFoundException.class,
            DefinitionNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundFromCommon(
            BusinessException ex,
            HttpServletRequest request) {

        log.warn("Resource not found: {}", ex.getMessage());

        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(RangeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRangeNotFound(
            RangeNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Range not found: {}", ex.getMessage());

        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request.getRequestURI());
    }



    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        log.warn("Business exception: {}", ex.getMessage());

        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error", ex);

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Gozlenilmez sistem xetasi bas verdi",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String message,
            String path) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}