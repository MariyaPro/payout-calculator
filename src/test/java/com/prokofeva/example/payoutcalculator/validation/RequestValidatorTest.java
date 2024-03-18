package com.prokofeva.example.payoutcalculator.validation;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor
public class RequestValidatorTest {
    @Autowired
    private RequestValidator requestValidator;

    @Test
    public void checkRequestTest() {
        RequestDto request = new RequestDto();

        request.setAvgSalary(293.0);
        request.setAmountOfDays(10);
        assertTrue(requestValidator.checkRequest(request));

        request.setAvgSalary(293.001);
        request.setAmountOfDays(10);
        assertFalse(requestValidator.checkRequest(request));

        request.setAvgSalary(293.0);
        request.setAmountOfDays(-10);
        assertFalse(requestValidator.checkRequest(request));

        request.setAvgSalary(293.0);
        request.setAmountOfDays(10);
        request.setFirstDay(LocalDate.of(2020, 3, 2));
        assertTrue(requestValidator.checkRequest(request));

        request.setAvgSalary(293.0);
        request.setAmountOfDays(10);
        request.setFirstDay(LocalDate.of(1000, 3, 2));
        assertFalse(requestValidator.checkRequest(request));

        request.setAvgSalary(293.0005);
        request.setAmountOfDays(-10);
        request.setFirstDay(LocalDate.of(1000, 3, 2));
        assertFalse(requestValidator.checkRequest(request));
    }


    @Test
    public void checkAvgSalaryTest() {
        RequestDto requestDto = new RequestDto();
        requestDto.setAvgSalary(100.0);
        requestDto.setAmountOfDays(10);
        assertTrue(requestValidator.checkRequest(requestDto));
        List<Violation> violationList = requestValidator.getViolationList();
        Set<String> setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertFalse(setField.contains("avgSalary"));

        requestDto.setAvgSalary(-100.0);
        assertFalse(requestValidator.checkRequest(requestDto));
        violationList = requestValidator.getViolationList();
        setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertTrue(setField.contains("avgSalary"));
        Set<String> setMessage = violationList.stream().map(Violation::getMessage).collect(Collectors.toSet());
        assertTrue(setMessage.contains("The value must be greater than zero."));

        requestDto.setAvgSalary(100.01);
        assertTrue(requestValidator.checkRequest(requestDto));
        violationList = requestValidator.getViolationList();
        setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertFalse(setField.contains("avgSalary"));

        requestDto.setAvgSalary(100.0678009);
        assertFalse(requestValidator.checkRequest(requestDto));
        violationList = requestValidator.getViolationList();
        setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertTrue(setField.contains("avgSalary"));
        setMessage = violationList.stream().map(Violation::getMessage).collect(Collectors.toSet());
        assertTrue(setMessage.contains("Round the value to two decimal places."));
    }

    @Test
    public void checkAmountOfDaysTest() {
        RequestDto requestDto = new RequestDto();
        requestDto.setAvgSalary(100.0);
        requestDto.setAmountOfDays(10);
        assertTrue(requestValidator.checkRequest(requestDto));
        List<Violation> violationList = requestValidator.getViolationList();
        Set<String> setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertFalse(setField.contains("amountOfDays"));

        requestDto.setAmountOfDays(-10);
        assertFalse(requestValidator.checkRequest(requestDto));
        violationList = requestValidator.getViolationList();
        setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertTrue(setField.contains("amountOfDays"));
        Set<String> setMessage = violationList.stream().map(Violation::getMessage).collect(Collectors.toSet());
        assertTrue(setMessage.contains("The value must be greater than zero."));

        requestDto.setAmountOfDays(101);
        assertFalse(requestValidator.checkRequest(requestDto));
        violationList = requestValidator.getViolationList();
        setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertTrue(setField.contains("amountOfDays"));
        setMessage = violationList.stream().map(Violation::getMessage).collect(Collectors.toSet());
        assertTrue(setMessage.contains("Value too high."));
    }

    @Test
    public void checkFirstDayTest() {
        RequestDto requestDto = new RequestDto();
        requestDto.setAvgSalary(100.0);
        requestDto.setAmountOfDays(10);
        requestDto.setFirstDay(LocalDate.of(2024, 2, 3));
        assertTrue(requestValidator.checkRequest(requestDto));
        List<Violation> violationList = requestValidator.getViolationList();
        Set<String> setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertFalse(setField.contains("firstDay"));

        requestDto.setFirstDay(LocalDate.of(1024, 2, 3));
        assertFalse(requestValidator.checkRequest(requestDto));
        violationList = requestValidator.getViolationList();
        setField = violationList.stream().map(Violation::getFieldName).collect(Collectors.toSet());
        assertTrue(setField.contains("firstDay"));
        Set<String> setMessage = violationList.stream().map(Violation::getMessage).collect(Collectors.toSet());
        assertTrue(setMessage.contains("Date too early."));
    }
}
