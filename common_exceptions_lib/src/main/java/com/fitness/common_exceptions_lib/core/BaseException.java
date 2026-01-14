package com.fitness.common_exceptions_lib.core;

import com.fitness.common_exceptions_lib.models.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;

    protected BaseException(
            String message,
            ErrorCode errorCode,
            HttpStatus status
    ) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
