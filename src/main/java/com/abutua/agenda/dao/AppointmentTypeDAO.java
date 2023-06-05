package com.abutua.agenda.dao;

import com.abutua.agenda.entites.AppointmentType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.Min;


@JsonInclude(Include.NON_NULL)
public class AppointmentTypeDAO {

    @Min(value=1, message = "Type id must be greater than or equal to 1.")
    private int id;
    
    private String type;

    public AppointmentTypeDAO(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public AppointmentType toEntity()
    {
        return new AppointmentType(id,type);
    }
    
}
