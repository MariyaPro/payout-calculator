package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@RequiredArgsConstructor
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

        String payout = this.calculatorServiceImpl.calculate(requestDto);

        requestDto.setFirstDay(LocalDate.of(2024, 3, 18));
        when(productionCalendarServiceImpl.getAmountOfHolidays(any(), any())).thenReturn(0);
        String payoutEquals = calculatorServiceImpl.calculate(requestDto);

        requestDto.setFirstDay(LocalDate.of(2024, 3, 7));
        when(productionCalendarServiceImpl.getAmountOfHolidays(any(), any())).thenReturn(1);
        String payoutNotEquals = calculatorServiceImpl.calculate(requestDto);

        verify(productionCalendarServiceImpl, times(2)).getAmountOfHolidays(any(), any());
        assertEquals(payout,"20.00");
        assertEquals(payout, payoutEquals);
        assertEquals(payoutNotEquals,"10.00");
        assertTrue(Double.parseDouble(payout) > Double.parseDouble(payoutNotEquals));
    }

}
