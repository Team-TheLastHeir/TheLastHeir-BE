package com.example.heir_project.exception.exception;

import com.example.heir_project.exception.ErrorCode;

public class AuthException extends RuntimeException {
    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}