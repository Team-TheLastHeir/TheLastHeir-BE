package com.example.heir_project.exception.handler;

import com.example.heir_project.exception.errorcode.AuthErrorCode;
import com.example.heir_project.exception.exception.AuthException;
import com.example.heir_project.exception.response.ApiResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Throwable exception = (Throwable) request.getAttribute("exception");
        if (exception == null) {
            exception = authException.getCause();
        }

        AuthErrorCode errorCode;
        if (exception instanceof ExpiredJwtException) {
            errorCode = AuthErrorCode.TOKEN_EXPIRED;
        } else if (exception instanceof JwtException || authException instanceof BadCredentialsException) {
            errorCode = AuthErrorCode.INVALID_TOKEN;
        } else {
            errorCode = AuthErrorCode.AUTHENTICATION_FAILED;
        }

        AuthException authEx = new AuthException(errorCode, exception);
        ApiResponseError errorResponse = ApiResponseError.of(authEx);

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}