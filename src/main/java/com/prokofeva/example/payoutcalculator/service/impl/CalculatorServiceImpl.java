package com.prokofeva.example.payoutcalculator.service.impl;

import com.prokofeva.example.payoutcalculator.domain.RequestDto;
import com.prokofeva.example.payoutcalculator.domain.ResponsePayoutDto;
import com.prokofeva.example.payoutcalculator.service.CalculatorService;
import com.prokofeva.example.payoutcalculator.service.ProductionCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private final ProductionCalendarService productionCalendarService;
    private static final double AVERAGE_NUMBER_OF_DAYS_IN_A_MONTH = 29.3;

    @Override
    public ResponsePayoutDto calculate(RequestDto requestDto) {
        double paymentOneDay = requestDto.getAvgSalary() / AVERAGE_NUMBER_OF_DAYS_IN_A_MONTH;
        double payout;
        if (requestDto.getFirstDay() == null)
            payout = paymentOneDay * requestDto.getAmountOfDays();
        else {
            int countHolidays = productionCalendarService.getAmountOfHolidays(requestDto.getFirstDay(), requestDto.getAmountOfDays());
            payout = (requestDto.getAmountOfDays() - countHolidays) * paymentOneDay;
        }
        return new ResponsePayoutDto(payout);
    }
}
