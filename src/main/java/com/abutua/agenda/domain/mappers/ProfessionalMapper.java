package com.abutua.agenda.domain.mappers;


import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.ProfessionalResponseDTO;

public class ProfessionalMapper {
    public static ProfessionalResponseDTO toResponseProfessionalDTO(Professional professional) {
        return new ProfessionalResponseDTO(professional.getId(), professional.getName(), professional.getPhone(), professional.isActive());
    }
}
