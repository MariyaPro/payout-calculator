package com.prokofeva.example.payoutcalculator.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ProductionCalendarService {
    Integer getAmountOfHolidays(LocalDate firstDay, Integer amountOfDays);
}
