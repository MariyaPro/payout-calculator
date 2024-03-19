package com.prokofeva.example.payoutcalculator.service;

import java.time.LocalDate;

public interface ProductionCalendarService {
    Integer getAmountOfHolidays(LocalDate firstDay, Integer amountOfDays);
}
