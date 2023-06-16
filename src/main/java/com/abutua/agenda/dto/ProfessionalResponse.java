package com.abutua.agenda.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public record ProfessionalResponse(
                long id,
                String name,
                String phone,
                boolean active

) {

}