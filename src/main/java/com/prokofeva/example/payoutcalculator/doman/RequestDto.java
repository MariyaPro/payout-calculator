package com.prokofeva.example.payoutcalculator.doman;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
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
