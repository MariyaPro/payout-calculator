package com.prokofeva.example.payoutcalculator.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DateProductionCalendarSourceException.class)
    public ResponseEntity<String> handle(DateProductionCalendarSourceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
