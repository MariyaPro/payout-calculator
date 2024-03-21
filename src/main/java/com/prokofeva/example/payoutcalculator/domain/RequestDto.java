package com.prokofeva.example.payoutcalculator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Getter
@Setter
public class RequestDto {
    @Digits(integer = 30, fraction = 2)
    @PositiveOrZero
    private Double avgSalary;

    @PositiveOrZero
    @Max(365)
    private Integer amountOfDays;

    private LocalDate firstDay;
}
