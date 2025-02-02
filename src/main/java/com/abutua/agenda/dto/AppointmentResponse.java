package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.domain.entites.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record AppointmentResponse(
        long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        AppointmentStatus status,
        ClientResponse client,
        AreaResponse area,
        ProfessionalResponse professional,
        AppointmentTypeResponse type,
        String comments) {

}
