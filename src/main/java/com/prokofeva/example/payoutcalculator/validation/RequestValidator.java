package com.prokofeva.example.payoutcalculator.validation;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class RequestValidator {
    private List<Violation> violationList ;

    public boolean checkRequest(RequestDto requestDto) {
        violationList = new ArrayList<>();
        checkAvgSalary(requestDto.getAvgSalary());
        checkAmountOfDays(requestDto.getAmountOfDays());
        if (requestDto.getFirstDay() != null) checkFirstDay(requestDto.getFirstDay());

        return violationList.isEmpty();
    }

    private void checkAvgSalary(Double avgSalary) {
        String fieldName = "avgSalary";

        if (avgSalary < 0.0)
            violationList.add(new Violation(fieldName, "The value must be greater than zero."));

        if (avgSalary * 100.0 - (int) (avgSalary * 100.0) > 0.0)
            violationList.add(new Violation(fieldName, "Round the value to two decimal places."));
    }

    private void checkAmountOfDays(Integer amountOfDays) {
        String fieldName = "amountOfDays";

        if (amountOfDays < 0)
            violationList.add(new Violation(fieldName, "The value must be greater than zero."));
        if (amountOfDays > 100)
            violationList.add(new Violation(fieldName, "Value too high."));
    }

    private void checkFirstDay(LocalDate firstDay) {
        String fieldName = "firstDay";

        if (firstDay.getYear() + 100 < LocalDate.now().getYear())
            violationList.add(new Violation(fieldName, "Date too early."));
    }
}