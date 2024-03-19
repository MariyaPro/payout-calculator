package com.prokofeva.example.payoutcalculator.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationError {
    private String fieldName;
    private String message;
}
