package com.prokofeva.example.payoutcalculator.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerExceptionAdvice {

    @ExceptionHandler(DateProductionCalendarSourceException.class)
    @ResponseBody
    public ResponseEntity<String> handle(DateProductionCalendarSourceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<List<ValidationError>> handle(MethodArgumentNotValidException e) {
        final List<ValidationError> errorsList = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(),
                                error.getDefaultMessage()
                        )
                ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsList);
    }
}