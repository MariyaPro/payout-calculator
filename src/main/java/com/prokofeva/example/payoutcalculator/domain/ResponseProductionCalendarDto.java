package com.prokofeva.example.payoutcalculator.domain;

import lombok.Getter;

import java.util.Map;

@Getter
public class ResponseProductionCalendarDto {
    private Map<String, String> statistic;
}