package com.fitness.common_exceptions_lib.core;

import com.fitness.common_exceptions_lib.models.ErrorCode;
import com.fitness.common_exceptions_lib.models.ErrorResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(
                        ex.getMessage(),
                        ex.getErrorCode(),
                        ex.getStatus().value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        ex.getBindingResult().getFieldError().getDefaultMessage(),
                        ErrorCode.VALIDATION_ERROR,
                        400,
                        Instant.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "Something went wrong",
                        ErrorCode.INTERNAL_ERROR,
                        500,
                        Instant.now()
                ));
    }
}
