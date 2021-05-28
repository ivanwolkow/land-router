package com.example.landrouter.controller;

import com.example.landrouter.exception.RouteNotFoundException;
import com.example.landrouter.exception.UnknownLandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RouteNotFoundException.class, UnknownLandException.class})
    public ResponseEntity<Object> routeNotFound(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().build();
    }
}
