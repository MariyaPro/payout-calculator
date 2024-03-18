package com.prokofeva.example.payoutcalculator.doman;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class RequestDto {

    private Double avgSalary;

    private Integer amountOfDays;

    private LocalDate firstDay;
}
