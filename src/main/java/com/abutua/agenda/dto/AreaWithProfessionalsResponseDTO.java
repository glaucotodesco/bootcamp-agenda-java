package com.abutua.agenda.dto;

import java.util.Set;

public record AreaWithProfessionalsResponseDTO(
        int id,
        String name,
        Set<ProfessionalResponseDTO> professionals) {

}
