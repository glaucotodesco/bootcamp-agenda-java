package com.abutua.agenda.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;

@JsonInclude(Include.NON_NULL)
public record ProfessionalDTO(
                @Min(value = 1, message = "Professional id must be greater than or equal to 1.") long id,
                String name,
                String phone,
                String email,
                String comments,
                Boolean active

) {

}
