package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
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

        String payout = calculatorServiceImpl.calculate(requestDto);

        requestDto.setFirstDay(LocalDate.of(2024, 3, 6));
        when(productionCalendarServiceImpl.getAmountOfHolidays(any(), any())).thenReturn(0);
        String payoutEquals = calculatorServiceImpl.calculate(requestDto);

        requestDto.setFirstDay(LocalDate.of(2024, 3, 7));
        when(productionCalendarServiceImpl.getAmountOfHolidays(any(), any())).thenReturn(1);
        String payoutNotEquals = calculatorServiceImpl.calculate(requestDto);

        verify(productionCalendarServiceImpl, times(2)).getAmountOfHolidays(any(), any());
        assertEquals(payout, "20.00");
        assertEquals(payout, payoutEquals);
        assertEquals(payoutNotEquals, "10.00");
    }
}



