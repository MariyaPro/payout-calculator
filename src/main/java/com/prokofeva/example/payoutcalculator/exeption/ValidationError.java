package com.prokofeva.example.payoutcalculator.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationError {
    private String fieldName;
    private String message;
}
