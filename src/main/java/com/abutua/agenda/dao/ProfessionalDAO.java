package com.abutua.agenda.dao;

import jakarta.validation.constraints.NotBlank;

public class ProfessionalDAO {

    @NotBlank(message = "Id can not be blank")
    private Long id;
    
    private String name;
    
    public ProfessionalDAO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
