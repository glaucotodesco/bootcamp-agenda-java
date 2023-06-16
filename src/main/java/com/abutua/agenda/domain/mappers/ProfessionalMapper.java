package com.abutua.agenda.domain.mappers;


import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.ProfessionalResponse;

public class ProfessionalMapper {
    public static ProfessionalResponse toResponseProfessionalDTO(Professional professional) {
        return new ProfessionalResponse(professional.getId(), professional.getName(), professional.getPhone(), professional.isActive());
    }
}
