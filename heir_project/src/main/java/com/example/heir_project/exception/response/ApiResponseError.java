package com.example.heir_project.exception.response;

import com.example.heir_project.exception.ErrorCode;
import com.example.heir_project.exception.exception.AuthException;
import lombok.Getter;

@Getter
public class ApiResponseError {
    private final int status;
    private final String code;
    private final String message;

    public ApiResponseError(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ApiResponseError of(AuthException ex) {
        ErrorCode code = ex.getErrorCode();
        return new ApiResponseError(code.getStatus(), code.getCode(), code.getMessage());
    }
}
