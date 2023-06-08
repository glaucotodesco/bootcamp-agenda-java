package com.abutua.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessionalSaveDTO(

        @NotBlank(message = "Name can not be blank") @Size(min = 3, max = 40, message = "Name length min=3 and max=40") String name,

        @Size(max = 40) String phone,

        @Email @Size(max = 40) String email,

        @Size(max = 1024) String comments,

        boolean active,

        Set<AreaDTO> areasdto) {

    public Professional toEntity() {
        Professional professional = new Professional(name, phone, email, comments, active);

        professional.getAreas().addAll(areasdto
                .stream()
                .map(a -> new Area(a.id()))
                .collect(Collectors.toList()));
        return professional;
    }

    public Iterable<Integer> getAreasId() {
        return areasdto.stream().map(a -> Integer.valueOf(a.id())).collect(Collectors.toList());
    }

}
