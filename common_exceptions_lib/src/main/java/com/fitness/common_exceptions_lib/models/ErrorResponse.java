package com.fitness.common_exceptions_lib.models;

import lombok.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private ErrorCode errorCode;
    private int status;
    private Instant timestamp;
}
