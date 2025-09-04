package com.example.heir_project.exception.exception;

public class PlayerLocationNotFoundException extends RuntimeException {
  public PlayerLocationNotFoundException(String message) {
    super(message);
  }

  public PlayerLocationNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}