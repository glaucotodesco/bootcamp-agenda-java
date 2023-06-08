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
        @FutureOrPresent(message = "Date must be equal to or later than the current date.") LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        @NotNull(message = "Area can not be null") @Valid AreaDTO area,
        @NotNull(message = "Client can not be null") @Valid ClientDTO client,
        @NotNull(message = "Professional can not be null") @Valid ProfessionalDTO professional,
        @NotNull(message = "Type can not be null") @Valid AppointmentTypeDTO type,
        String comment

) {

    public Appointment toEntity() {
        Appointment appointment = new Appointment();

        appointment.setDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setComment(comment);
        appointment.setType(type.toEntity());
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setClient(new Client(client.id()));
        appointment.setProfessional(new Professional(professional.id()));
        appointment.setArea(new Area(area.id()));

        return appointment;
    }
}
