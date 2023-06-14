package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.entites.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record AppointmentResponseDTO(
        long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        AppointmentStatus status,
        ClientResponseDTO client,
        AreaResponseDTO area,
        ProfessionalResponseDTO professional,
        AppointmentTypeResponseDTO type,
        String comments) {

}
