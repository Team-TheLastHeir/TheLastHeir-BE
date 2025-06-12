package com.example.heir_project.exception.errorcode;

import com.example.heir_project.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
    TOKEN_EXPIRED(401, "A001", "만료된 토큰"),
    INVALID_TOKEN(401, "A002", "유효하지 않은 토큰"),
    AUTHENTICATION_FAILED(401, "A003", "인증 실패");

    private final int status;
    private final String code;
    private final String message;

    AuthErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
