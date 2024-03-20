package com.prokofeva.example.payoutcalculator.service.impl;

import com.prokofeva.example.payoutcalculator.domain.ResponseProductionCalendarDto;
import com.prokofeva.example.payoutcalculator.exeption.DateProductionCalendarSourceException;
import com.prokofeva.example.payoutcalculator.service.ProductionCalendarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ProductionCalendarServiceImpl implements ProductionCalendarService {

    @Value("${production_calendar.source}")
    private String sourcePath;
    @Value("${production_calendar.country}")
    private String country;
    @Value("${production_calendar.format_response}")
    private String formatResponse;

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    @Override
    public Integer getAmountOfHolidays(LocalDate firstDay, Integer amountOfDays) {
        String url = buildUrl(firstDay, amountOfDays);

        ResponseEntity<ResponseProductionCalendarDto> responseEntity = restTemplate.getForEntity(url, ResponseProductionCalendarDto.class);
        Optional<ResponseProductionCalendarDto> optionalDto = Optional.ofNullable(responseEntity.getBody());

        return Integer.parseInt(optionalDto.orElseThrow(DateProductionCalendarSourceException::new).getStatistic().get("holidays"));
    }

    private String buildUrl(LocalDate firstDay, Integer amountOfDays) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String startDay = firstDay.format(pattern);
        String finishDay = firstDay.plusDays(amountOfDays - 1).format(pattern);

        return sourcePath + country + '/' + startDay + '-' + finishDay + '/' + formatResponse;
    }
}
