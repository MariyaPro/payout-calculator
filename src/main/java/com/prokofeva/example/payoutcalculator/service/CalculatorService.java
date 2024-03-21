package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.domain.RequestDto;
import com.prokofeva.example.payoutcalculator.domain.ResponsePayoutDto;

public interface CalculatorService {
    ResponsePayoutDto calculate(RequestDto requestDto);
}
