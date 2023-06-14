package com.abutua.agenda.dto;

import java.time.LocalTime;

public record TimeSlotResponseDTO(
        LocalTime startTime,
        LocalTime endTime,
        boolean available) {
}
