package com.abutua.agenda.dto;

import java.time.OffsetTime;

public interface TimeSlotOffSet {
    OffsetTime getStartTime();
    OffsetTime getEndTime();
    boolean isAvailable();
}
