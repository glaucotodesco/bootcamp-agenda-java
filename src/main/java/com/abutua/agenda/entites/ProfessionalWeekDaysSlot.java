package com.abutua.agenda.entites;

import java.time.DayOfWeek;

public interface ProfessionalWeekDaysSlot {
    DayOfWeek getWeekDay();
    int getTotalSlots();
}
