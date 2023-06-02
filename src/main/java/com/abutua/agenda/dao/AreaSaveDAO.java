package com.abutua.agenda.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AreaSaveDAO {

    @NotBlank(message = "Name can not be blank")
    @Size(min = 3, max = 40, message = "Name length min=3 and max=40")
    private String name;

    private Set<ProfessionalDAO> professionalsDAO = new HashSet<ProfessionalDAO>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area toEntity() {
        Area area = new Area(name);
        area.getProfessionals().addAll(professionalsDAO
                                        .stream()
                                        .map(p -> new Professional(p.getId()))
                                        .collect(Collectors.toList()));
        return area;
    }


    public Set<ProfessionalDAO> getProfessionals() {
        return professionalsDAO;
    }

    public void setProfessionals(Set<ProfessionalDAO> professionals) {
        this.professionalsDAO = professionals;
    }

    public Iterable<Long> getProfessionalsId() {
        return professionalsDAO.stream().map( p -> Long.valueOf(p.getId())).collect(Collectors.toList());
    }

}
