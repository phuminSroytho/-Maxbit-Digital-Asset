package com.example.maxbitdigitalasset.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    private HttpHeaders httpHeaders;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception exception) {
        HttpHeaders newHttpHeader = new HttpHeaders();
        newHttpHeader.addAll(httpHeaders);

        return new ResponseEntity<>(
                newHttpHeader,
                HttpStatus.EXPECTATION_FAILED);
    }
}
