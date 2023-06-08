package com.abutua.agenda.dto;

import java.time.LocalTime;

public record TimeSlotDTO (
    LocalTime startTime,
    LocalTime endTime,
    boolean available
) {}
    