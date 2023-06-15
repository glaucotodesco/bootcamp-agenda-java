package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.AppointmentStatus;
import com.abutua.agenda.domain.entites.AppointmentType;
import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.entites.Professional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record AppointmentRequestDTO(
        @FutureOrPresent(message = "A data deve ser maior ou igual a data corrente.") 
        LocalDate date,
        
        LocalTime startTime,
        
        LocalTime endTime,
        
        @NotNull(message = "Area não pode ser nula.")
        @Valid 
        IntegerIdDTO area,
        
        @NotNull(message = "Cliente não pode ser nulo.") 
        @Valid 
        LongIdDTO client,
        
        @NotNull(message = "Profissonal não pode ser nulo.") 
        @Valid 
        LongIdDTO professional,
        
        @NotNull(message = "Tipo do atendimento não pode ser nulo.") 
        @Valid 
        IntegerIdDTO type,
        
        String comments

) {

    public Appointment toEntity() {
        Appointment appointment = new Appointment();

        appointment.setDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setComments(comments);
        appointment.setType(new AppointmentType(type.id()));
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setClient(new Client(client.id()));
        appointment.setProfessional(new Professional(professional.id()));
        appointment.setArea(new Area(area.id()));

        return appointment;
    }
}
