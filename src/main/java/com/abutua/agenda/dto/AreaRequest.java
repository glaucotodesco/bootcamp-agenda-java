package com.abutua.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.constraints.NotBlank;

public record AreaRequest(
        
        @NotBlank(message = "Nome requirido")
        String name,
        
        Set<LongIdDTO> professionals) {


    public Iterable<Long> getProfessionalsId() {
        return professionals.stream().map(p -> Long.valueOf(p.id())).collect(Collectors.toList());
    }

}
