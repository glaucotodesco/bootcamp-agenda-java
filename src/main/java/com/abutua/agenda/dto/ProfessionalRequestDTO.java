package com.abutua.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessionalRequestDTO(
        
        @NotBlank(message = "Nome requirido.") 
        String name,
        
        @Size(max = 40)
        String phone,
        
        boolean active,
        
        Set<AreaResponseDTO> areas) {

    public Professional toEntity() {
        Professional professional = new Professional(name, phone, active);

        professional.getAreas().addAll(areas
                .stream()
                .map(a -> new Area(a.id()))
                .collect(Collectors.toList()));
        return professional;
    }

    public Iterable<Integer> getAreasId() {
        return areas.stream().map(a -> Integer.valueOf(a.id())).collect(Collectors.toList());
    }

}
