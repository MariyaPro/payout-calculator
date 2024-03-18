package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.doman.RequestDto;
import org.springframework.stereotype.Service;

@Service
public interface CalculatorService {
    String calculate(RequestDto requestDto);
}
