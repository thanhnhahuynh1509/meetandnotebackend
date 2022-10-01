package com.tcn.meetandnote.exception.advice;

import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.exception.response.ExceptionResponse;
import com.tcn.meetandnote.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), time, request.getDescription(false));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConflictException(ConflictException ex, WebRequest request) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), time, request.getDescription(false));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleUsernameException(UsernameNotFoundException ex, WebRequest request) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), time, request.getDescription(false));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        return new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), time, request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception ex, WebRequest request) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), time, request.getDescription(false));
    }

    }
