package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.domain.RequestDto;
import com.prokofeva.example.payoutcalculator.domain.ResponsePayoutDto;
import com.prokofeva.example.payoutcalculator.service.impl.CalculatorServiceImpl;
import com.prokofeva.example.payoutcalculator.service.impl.ProductionCalendarServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CalculatorServiceImplTest {

    @InjectMocks
    private CalculatorServiceImpl calculatorServiceImpl;

    @Mock
    private ProductionCalendarServiceImpl productionCalendarServiceImpl;

    @Test
    public void calculateTest() {
        RequestDto requestDto = new RequestDto();
        requestDto.setAvgSalary(293.0);
        requestDto.setAmountOfDays(2);

        ResponsePayoutDto payout = calculatorServiceImpl.calculate(requestDto);

        requestDto.setFirstDay(LocalDate.of(2024, 3, 6));
        when(productionCalendarServiceImpl.getAmountOfHolidays(any(), any())).thenReturn(0);
        ResponsePayoutDto payoutEquals = calculatorServiceImpl.calculate(requestDto);

        requestDto.setFirstDay(LocalDate.of(2024, 3, 7));
        when(productionCalendarServiceImpl.getAmountOfHolidays(any(), any())).thenReturn(1);
        ResponsePayoutDto payoutNotEquals = calculatorServiceImpl.calculate(requestDto);

        verify(productionCalendarServiceImpl, times(2)).getAmountOfHolidays(any(), any());
        assertEquals(payout.getAmount(), 20.0);
        assertEquals(payout.getAmount(), payoutEquals.getAmount());
        assertEquals(payoutNotEquals.getAmount(), 10.0);
    }
}



