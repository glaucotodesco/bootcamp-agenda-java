package com.abutua.agenda.dto;

import java.util.List;

public record AreaWithProfessionalDTO(
        int id,
        String name,
        List<ProfessionalDTO> professionals) {

}
