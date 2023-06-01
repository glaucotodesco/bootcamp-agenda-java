package com.abutua.agenda.dao;

import java.util.ArrayList;
import java.util.List;

public class AreaWithProfessionalDAO {
    private int id;
    private String name;
    private List<ProfessionalDAO>  professionals = new ArrayList<ProfessionalDAO>();

    public AreaWithProfessionalDAO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<ProfessionalDAO> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<ProfessionalDAO> professionals) {
        this.professionals = professionals;
    }

    
    

}
