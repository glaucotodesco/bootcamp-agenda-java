package com.abutua.agenda.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;



@JsonInclude(Include.NON_NULL)
public record AreaDTO (
     @Min(value=1, message = "Area id must be greater than or equal to 1.")
     int id,
    
     String name) {
}