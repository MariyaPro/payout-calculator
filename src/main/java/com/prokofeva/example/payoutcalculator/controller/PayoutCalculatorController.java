package com.prokofeva.example.payoutcalculator.controller;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import com.prokofeva.example.payoutcalculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class PayoutCalculatorController {
    private final CalculatorService calculatorService;

    @GetMapping("/calculate")
    @ResponseBody
    public ResponseEntity<Object> calculate(@Valid @RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(calculatorService.calculate(requestDto));
    }
}
