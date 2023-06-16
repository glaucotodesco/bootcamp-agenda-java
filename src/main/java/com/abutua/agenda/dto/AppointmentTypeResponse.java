package com.abutua.agenda.dto;

import com.abutua.agenda.domain.entites.AppointmentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record AppointmentTypeResponse(
        int id,
        String type) {

    public AppointmentType toEntity() {
        return new AppointmentType(id, type);
    }

}
