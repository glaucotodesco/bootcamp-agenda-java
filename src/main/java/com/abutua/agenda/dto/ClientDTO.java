package com.abutua.agenda.dto;

import java.time.LocalDate;

import com.abutua.agenda.entites.Gender;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;


@JsonInclude(Include.NON_NULL)
public record ClientDTO(
    @Min(value=1, message = "Client id must be greater than or equal to 1.")
    long id,

    String name,
    String phone,
    String email,
    String comment,
    LocalDate dateOfBirth,
    Gender gender
    
) {

}

    
