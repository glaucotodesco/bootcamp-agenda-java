package com.abutua.agenda.domain.entites.converters;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter 
public class DayOfWeekIntegerConverter implements AttributeConverter<DayOfWeek, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            case THURSDAY:
                return 5;
            case FRIDAY:
                return 6;
            case SATURDAY:
                return 7;
            case SUNDAY:
                return 1;
            default:
                throw new IllegalArgumentException("Dia da semana inválido: " + dayOfWeek);
        }
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Integer dayOfWeek) {
        switch (dayOfWeek) {
            case 2:
                return DayOfWeek.MONDAY;
            case 3:
                return DayOfWeek.TUESDAY;
            case 4:
                return DayOfWeek.WEDNESDAY;
            case 5:
                return DayOfWeek.THURSDAY;
            case 6:
                return DayOfWeek.FRIDAY;
            case 7:
                return DayOfWeek.SATURDAY;
            case 1:
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Valor inválido para o dia da semana no banco de dados: " + dayOfWeek);
        }
    }
}