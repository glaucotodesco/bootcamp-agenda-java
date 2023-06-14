package com.abutua.agenda.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public record AreaResponseDTO(
          int id,
          String name) {
}
