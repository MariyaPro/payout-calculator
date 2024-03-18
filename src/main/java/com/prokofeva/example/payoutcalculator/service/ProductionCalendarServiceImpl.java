package com.prokofeva.example.payoutcalculator.service;

import com.prokofeva.example.payoutcalculator.doman.ResponseProductionCalendarDto;
import com.prokofeva.example.payoutcalculator.exeption.DateProductionCalendarSourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductionCalendarService {

    @Value("${production_calendar.source}")
    private String sourcePath;
    @Value("${production_calendar.country}")
    private String country;
    @Value("${production_calendar.format_response}")
    private String formatResponse;

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();


    public Integer getAmountOfHolidays(LocalDate firstDay, Integer amountOfDays) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String startDay = firstDay.format(pattern);
        String finishDay = firstDay.plusDays(amountOfDays).format(pattern);

        String url = buildUrl(startDay, finishDay);

        ResponseEntity<ResponseProductionCalendarDto> responseEntity = restTemplate.getForEntity(url, ResponseProductionCalendarDto.class);
        Optional<ResponseProductionCalendarDto> optionalDto = Optional.ofNullable(responseEntity.getBody());

        return optionalDto.orElseThrow(DateProductionCalendarSourceException::new).getDays().size();
    }

    private String buildUrl(String startDay, String finishDay) {
        return sourcePath + country + '/' + startDay + '-' + finishDay + '/' + formatResponse;
    }

}
