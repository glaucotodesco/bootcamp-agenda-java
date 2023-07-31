package com.abutua.agenda.domain.services.usecases.read;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.entites.ProfessionalScheduleDays;
import com.abutua.agenda.domain.entites.ProfessionalWeekDaysSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.web.resources.exceptions.ParameterException;

/**
 * 
 * @param professionalId
 * @param month          Valid value and now or future
 * @param year           Now or future
 * 
 * @return
 *
 * @throws ParameterException("Invalid parameter month value.");
 * @throws ParameterException("Invalid parameter year value. Year must be
 *                                     greater than or equal to the current
 *                                     year.")
 * @throws ParameterException("Invalid parameter month value. Month and Year
 *                                     must be greater than or equal to the curr
 */

@Service
public class SearchProfessionalAvailabilityDaysUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;
 
    public List<Integer> executeUseCase(long professionalId,LocalDate start, LocalDate end) {
        return appointmentRepository.getAvailabilyDays(professionalId, start, end);
    }
    
}
