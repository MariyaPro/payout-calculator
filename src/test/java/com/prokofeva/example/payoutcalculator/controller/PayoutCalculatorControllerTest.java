package com.prokofeva.example.payoutcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import com.prokofeva.example.payoutcalculator.exeption.ValidationError;
import com.prokofeva.example.payoutcalculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class PayoutCalculatorControllerTest {

    @InjectMocks
    @Autowired
    private PayoutCalculatorController payoutCalculatorController;

    @Mock
    private CalculatorService calculatorService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void calculateTest() {
        RequestDto request = new RequestDto();

        request.setAvgSalary(293.0);
        request.setAmountOfDays(2);
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

    @Test
    public void calculateFieldAvgSalaryFailTest() throws Exception {
        ValidationError validationError1 = new ValidationError("avgSalary", "must be greater than or equal to 0");
        ValidationError validationError2 = new ValidationError("avgSalary", "numeric value out of bounds (<30 digits>.<2 digits> expected)");
        List<ValidationError> errorList1 = List.of(validationError1);
        List<ValidationError> errorList2 = List.of(validationError2);

        RequestDto requestDto = new RequestDto();
        requestDto.setAvgSalary(-293.0);
        requestDto.setAmountOfDays(2);
        String s = mockMvc.perform(
                        get("/calculate")
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertEquals(s, objectMapper.writeValueAsString(errorList1));

        requestDto.setAvgSalary(293.0009);
        mockMvc.perform(
                        get("/calculate")
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorList2)));
    }

    @Test
    public void calculateFieldAmountOfDaysFailTest() throws Exception {
        ValidationError validationError1 = new ValidationError("amountOfDays", "must be greater than or equal to 0");
        ValidationError validationError2 = new ValidationError("amountOfDays", "must be less than or equal to 365");
        List<ValidationError> errorList1 = List.of(validationError1);
        List<ValidationError> errorList2 = List.of(validationError2);

        RequestDto requestDto = new RequestDto();
        requestDto.setAvgSalary(293.0);
        requestDto.setAmountOfDays(-2);
        String s = mockMvc.perform(
                        get("/calculate")
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertEquals(s, objectMapper.writeValueAsString(errorList1));

        requestDto.setAmountOfDays(501);
        mockMvc.perform(
                        get("/calculate")
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorList2)));
    }
}
