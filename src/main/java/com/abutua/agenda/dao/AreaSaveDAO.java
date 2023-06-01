package com.abutua.agenda.dao;

import com.abutua.agenda.entites.Area;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AreaSaveDAO {
    @NotBlank(message = "Name can not be blank")
    @Size(min = 3, max = 40, message = "Name length min=3 and max=40")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area toEntity() {
        Area area = new Area(name);
        return area;
    }
}
