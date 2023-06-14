package com.abutua.agenda.dto;

import java.util.List;

public record AreaWithProfessionalsResponseDTO(
        int id,
        String name,
        List<ProfessionalResponseDTO> professionals) {

}
