package com.abutua.agenda.dto;

import com.abutua.agenda.entites.AppointmentType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.Min;

@JsonInclude(Include.NON_NULL)
public record AppointmentTypeDTO(
        @Min(value = 1, message = "Type id must be greater than or equal to 1.") int id,
        String type) {

    public AppointmentType toEntity() {
        return new AppointmentType(id, type);
    }

}
