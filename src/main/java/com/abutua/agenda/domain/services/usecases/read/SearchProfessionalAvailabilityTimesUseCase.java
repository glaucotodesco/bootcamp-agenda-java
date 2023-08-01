package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entites.converters.DayOfWeekIntegerConverter;
import com.abutua.agenda.domain.entites.converters.TimeSlotConverter;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.dto.TimeSlotResponse;


@Service
public class SearchProfessionalAvailabilityTimesUseCase {

    
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<TimeSlotResponse> executeUseCase(LocalDate date, Long professionalId) {
        DayOfWeekIntegerConverter converter = new DayOfWeekIntegerConverter();
        var data = appointmentRepository.findWorkScheduleFromProfessionalIdByDate(professionalId, date, converter.convertToDatabaseColumn(date.getDayOfWeek()));
        return TimeSlotConverter.convertList(data);
    }
 
}
