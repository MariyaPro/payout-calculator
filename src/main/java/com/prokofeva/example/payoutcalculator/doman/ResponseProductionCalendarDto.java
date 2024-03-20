package com.prokofeva.example.payoutcalculator.doman;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@RequiredArgsConstructor
@Setter
@Getter
public class ResponseProductionCalendarDto {
    private Map<String, String> statistic;
}