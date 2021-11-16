package com.example.deldrugs.controller;


import com.example.deldrugs.dto.exception.ExceptionDTO;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ExceptionDTO> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionDTO.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.name())
                .path(request.getRequestURI())
                .message(accessDeniedException.getMessage()).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ExceptionDTO> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionDTO.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .path(request.getRequestURI())
                .message(exception.getMessage()).build());
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<ExceptionDTO> handleJwtException(JwtException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionDTO.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .path(request.getRequestURI())
                .message(exception.getMessage()).build());
    }
}
