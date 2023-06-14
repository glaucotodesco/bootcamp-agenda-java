package com.abutua.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.constraints.NotBlank;


public record AreaRequestDTO(
        
        @NotBlank(message = "Nome requirido")
        String name,
        
        Set<ProfessionalResponseDTO> professionals) {

    public Area toEntity() {
        Area area = new Area(name);
        area.getProfessionals().addAll(professionals
                .stream()
                .map(p -> new Professional(p.id()))
                .collect(Collectors.toList()));
        return area;
    }

    public Iterable<Long> getProfessionalsId() {
        return professionals.stream().map(p -> Long.valueOf(p.id())).collect(Collectors.toList());
    }

}
