package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private final ProductionCalendarService productionCalendarService;
    private final Double AVERAGE_NUMBER_OF_DAYS_IN_A_MONTH = 29.3;

    @Override
    public String calculate(RequestDto requestDto) {
        double paymentOneDay = requestDto.getAvgSalary() / AVERAGE_NUMBER_OF_DAYS_IN_A_MONTH;
        double payout;
        if (requestDto.getFirstDay() == null)
            payout = paymentOneDay * requestDto.getAmountOfDays();
        else {
            int countHolidays = productionCalendarService.getAmountOfHolidays(requestDto.getFirstDay(), requestDto.getAmountOfDays());
            payout = (requestDto.getAmountOfDays() - countHolidays) * paymentOneDay;
        }
        return String.format("%.2f", payout);
    }
}
