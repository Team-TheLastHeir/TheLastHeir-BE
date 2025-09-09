package com.heir_project.exception;

public interface ErrorCode {
    int getStatus();
    String getCode();
    String getMessage();
}