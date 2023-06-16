package com.abutua.agenda.dto;

import java.util.List;

public record ProfessionalWithAreasResponse(
        long id,
        String name,
        String phone,
        boolean active,
        List<AreaResponse> areas) {

}
