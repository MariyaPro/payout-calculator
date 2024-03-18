package com.prokofeva.example.payoutcalculator.controller;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import com.prokofeva.example.payoutcalculator.service.CalculatorService;
import com.prokofeva.example.payoutcalculator.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@RequiredArgsConstructor
public class PayoutCalculatorControllerTest {

    @InjectMocks
    private PayoutCalculatorController payoutCalculatorController;

    @Mock
    private CalculatorService calculatorService;
    @Mock
    private RequestValidator requestValidator;

    @Test
    public void calculateTest() {
        RequestDto request = new RequestDto();

        request.setAvgSalary(293.0);
        request.setAmountOfDays(2);
        when(requestValidator.checkRequest(any())).thenReturn(true);
        when(calculatorService.calculate(any())).thenReturn("20.00");
        ResponseEntity<Object> responseEntity = payoutCalculatorController.calculate(request);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), "20.00");

        request.setFirstDay(LocalDate.of(2024, 3, 7));
        when(calculatorService.calculate(any())).thenReturn("10.00");
        responseEntity = payoutCalculatorController.calculate(request);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), "10.00");
    }

}
