package com.abutua.agenda.domain.entites;

import java.time.DayOfWeek;

public interface ProfessionalWeekDaysSlot {
    DayOfWeek getWeekDay();
    int getTotalSlots();
}
