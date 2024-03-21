package com.prokofeva.example.payoutcalculator.service.impl;

import com.prokofeva.example.payoutcalculator.domain.ResponseProductionCalendarDto;
import com.prokofeva.example.payoutcalculator.exeption.DateProductionCalendarSourceException;
import com.prokofeva.example.payoutcalculator.service.ProductionCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductionCalendarServiceImpl implements ProductionCalendarService {

    @Value("${production_calendar.source}")
    private String sourcePath;
    @Value("${production_calendar.country}")
    private String country;
    @Value("${production_calendar.format_response}")
    private String formatResponse;
    private final WebClient webClient = WebClient.builder().build();

    @Override
    public Integer getAmountOfHolidays(LocalDate firstDay, Integer amountOfDays) {
        String period = buildPeriod(firstDay, amountOfDays);

        ResponseProductionCalendarDto productionCalendarDtoMono = webClient.get()
                .uri(String.join("/", sourcePath, country, period, formatResponse))
                .retrieve()
                .bodyToMono(ResponseProductionCalendarDto.class)
                .onErrorResume(e -> Mono.error(DateProductionCalendarSourceException::new))
                .block();

        return Integer.parseInt(Optional
                .ofNullable(productionCalendarDtoMono)
                .orElseThrow(DateProductionCalendarSourceException::new)
                .getStatistic().get("holidays"));
    }

    private String buildPeriod(LocalDate firstDay, Integer amountOfDays) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String startDayInclusive = firstDay.format(pattern);
        String finishDayInclusive = firstDay.plusDays(amountOfDays - 1).format(pattern);

        return String.join("-", startDayInclusive, finishDayInclusive);
    }
}