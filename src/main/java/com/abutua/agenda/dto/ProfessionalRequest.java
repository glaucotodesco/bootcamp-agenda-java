package com.abutua.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessionalRequest(
        
        @NotBlank(message = "Nome requirido.") 
        String name,
        
        @Size(max = 40)
        String phone,
        
        boolean active,
        
        Set<AreaResponse> areas) {

    public Iterable<Integer> getAreasId() {
        return areas.stream().map(a -> Integer.valueOf(a.id())).collect(Collectors.toList());
    }

}
