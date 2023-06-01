package com.abutua.agenda.dao;

import java.util.ArrayList;
import java.util.List;


public class ProfessionalWithAreaDAO extends ProfessionalDAO {

    private List<AreaDAO>  areas = new ArrayList<AreaDAO>();

    public ProfessionalWithAreaDAO(long id, String name, String phone, String email, String comments, boolean active) {
        super(id, name, phone, email, comments, active);
    }

    public List<AreaDAO> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaDAO> areas) {
        this.areas = areas;
    }

    
}
