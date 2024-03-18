package com.prokofeva.example.payoutcalculator.controller;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import com.prokofeva.example.payoutcalculator.service.CalculatorService;
import com.prokofeva.example.payoutcalculator.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PayoutCalculatorController {
    private final CalculatorService calculatorService;
    private final RequestValidator requestValidator;

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestBody RequestDto requestDto) {
        return requestValidator.checkRequest(requestDto)
                ? ResponseEntity.status(HttpStatus.OK).body(calculatorService.calculate(requestDto))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(requestValidator.getViolationList());
    }
}
