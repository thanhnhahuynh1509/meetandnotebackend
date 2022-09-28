package com.tcn.meetandnote.exception.advice;

import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

}
