package com.abutua.agenda.domain.entites.converters;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.abutua.agenda.dto.TimeSlotOffSet;
import com.abutua.agenda.dto.TimeSlotResponse;

public class TimeSlotConverter {
    public static TimeSlotResponse convert(TimeSlotOffSet timeSlotResponse) {
        LocalTime startTime = timeSlotResponse.getStartTime().toLocalTime();
        LocalTime endTime = timeSlotResponse.getEndTime().toLocalTime();
        boolean isAvailable = timeSlotResponse.isAvailable();
        return new TimeSlotResponse(startTime, endTime, isAvailable);
    }

    public static List<TimeSlotResponse> convertList(List<TimeSlotOffSet> timeSlotResponses) {
        return timeSlotResponses.stream()
                .map(TimeSlotConverter::convert)
                .collect(Collectors.toList());
    }
}