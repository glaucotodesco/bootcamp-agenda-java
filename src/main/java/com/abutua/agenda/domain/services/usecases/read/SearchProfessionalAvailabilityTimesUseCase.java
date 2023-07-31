package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abutua.agenda.domain.repositories.AppointmentRepository;

import com.abutua.agenda.dto.TimeSlotResponse;

@Service
public class SearchProfessionalAvailabilityTimesUseCase {

    
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<TimeSlotResponse> executeUseCase(LocalDate date, Long professionalId) {
        System.out.println(date.getDayOfWeek().getValue());
        return  appointmentRepository.findWorkScheduleFromProfessionalIdByDate(professionalId, date, date.getDayOfWeek().getValue()-1);
    }
 
}
