package com.prokofeva.example.payoutcalculator.exeption;

import lombok.Getter;

@Getter
public class DateProductionCalendarSourceException extends RuntimeException {
    private final String message = "An error occurred when calculating the number of holidays in a given period.";
}
