package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.AppointmentStatus;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record AppointmentSaveDTO(
        @FutureOrPresent(message = "A data deve ser maior ou igual a data corrente.") 
        LocalDate date,
        
        LocalTime startTime,
        
        LocalTime endTime,
        
        @NotNull(message = "Area não pode ser nula.")
        @Valid 
        AreaDTO area,
        
        @NotNull(message = "Cliente não pode ser nulo.") 
        @Valid 
        ClientDTO client,
        
        @NotNull(message = "Profissonal não pode ser nulo.") 
        @Valid 
        ProfessionalDTO professional,
        
        @NotNull(message = "Tipo do atendimento não pode ser nulo.") 
        @Valid 
        AppointmentTypeDTO type,
        
        String comments

) {

    public Appointment toEntity() {
        Appointment appointment = new Appointment();

        appointment.setDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setComments(comments);
        appointment.setType(type.toEntity());
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setClient(new Client(client.id()));
        appointment.setProfessional(new Professional(professional.id()));
        appointment.setArea(new Area(area.id()));

        return appointment;
    }
}
