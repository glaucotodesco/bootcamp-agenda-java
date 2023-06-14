package com.abutua.agenda.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkScheduleItemResponseDTO(
        long id,
        DayOfWeek dayOfWeek,
        LocalTime starTime,
        LocalTime endTime) {

}
