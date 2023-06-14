package com.abutua.agenda.dto;

import java.util.List;

public record ProfessionalWithAreasResponseDTO(
        long id,
        String name,
        String phone,
        boolean active,
        List<AreaResponseDTO> areas) {

}
