package com.abutua.agenda.dto;

import java.time.OffsetTime;

public interface TimeSlotResponse {
    OffsetTime getStartTime();
    OffsetTime getEndTime();
    boolean isAvailable();
}
