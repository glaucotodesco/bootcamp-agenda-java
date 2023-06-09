package com.abutua.agenda.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ClientDTO(
        long id,
        String name,
        String phone,
        LocalDate dateOfBirth
) {

}
