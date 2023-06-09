package com.abutua.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AreaSaveDTO(
        @NotBlank(message = "Nome requirido") @Size(min = 3, max = 40, message = "Nome deve ter no mímino 3 e no máximo 40 caracteres") String name,
        Set<ProfessionalDTO> professionalsdto) {

    public Area toEntity() {
        Area area = new Area(name);
        area.getProfessionals().addAll(professionalsdto
                .stream()
                .map(p -> new Professional(p.id()))
                .collect(Collectors.toList()));
        return area;
    }

    public Iterable<Long> getProfessionalsId() {
        return professionalsdto.stream().map(p -> Long.valueOf(p.id())).collect(Collectors.toList());
    }

}
