package com.assignment.pentasecurity_be.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        String code, // 선택적: "VALIDATION_ERROR" 같은 식별 코드
        String path, // 선택적: 요청 경로
        LocalDateTime timestamp
) {
    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                message,
                status.name(),
                null,
                LocalDateTime.now()
        );
    }

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(
                status.value(),
                message,
                status.name(),
                path,
                LocalDateTime.now()
        );
    }
}

