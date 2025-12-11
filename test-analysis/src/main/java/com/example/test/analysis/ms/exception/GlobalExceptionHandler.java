package com.example.test.analysis.ms.exception;

import com.example.test.analysis.ms.dto.response.exceptionr.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateException ex,
                                             HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(StatusAlreadyExistsException.class)
    public ResponseEntity <?> handleStatusAlreadyExists(StatusAlreadyExistsException ex,
                                                    HttpServletRequest request){

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(AnalysisCategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFound(AnalysisCategoryNotFoundException ex,
                                                    HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(build( HttpStatus.NOT_FOUND, ex.getMessage(),request.getRequestURI()));
    }

    @ExceptionHandler(DefinitionNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFound(DefinitionNotFoundException ex,
                                                    HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(build( HttpStatus.NOT_FOUND, ex.getMessage(),request.getRequestURI()));
    }


    @ExceptionHandler(UnitAlreadyExistsException.class)
    public ResponseEntity<?> handleUnitAlreadyExists(UnitAlreadyExistsException ex,
                                                     HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(build(HttpStatus.CONFLICT, ex.getMessage(),request.getRequestURI()));
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleUnitAlreadyExists(AlreadyExistsException ex,
                                                     HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(build(HttpStatus.CONFLICT, ex.getMessage(),request.getRequestURI()));
    }

    @ExceptionHandler(RangeNotFoundException.class)
    public ResponseEntity<?> handleRangeNotFound(RangeNotFoundException ex,
                                                 HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(build( HttpStatus.NOT_FOUND, ex.getMessage(),request.getRequestURI()));
    }



    private ErrorResponse build(HttpStatus status, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }
}
