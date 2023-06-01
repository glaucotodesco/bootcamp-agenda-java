package com.abutua.agenda.dao;

import java.util.ArrayList;
import java.util.List;

public class AreaWithProfessionalDAO extends AreaDAO{
    private List<ProfessionalDAO>  professionals = new ArrayList<ProfessionalDAO>();

    public AreaWithProfessionalDAO(int id, String name) {
        super(id, name);
    }

    public List<ProfessionalDAO> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<ProfessionalDAO> professionals) {
        this.professionals = professionals;
    }
}
