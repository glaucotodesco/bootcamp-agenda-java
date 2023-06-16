package com.abutua.agenda.dto;

import java.util.Set;

public record AreaWithProfessionalsResponse(
        int id,
        String name,
        Set<ProfessionalResponse> professionals) {

}
