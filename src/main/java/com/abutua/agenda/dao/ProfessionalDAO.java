package com.abutua.agenda.dao;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;


@JsonInclude(Include.NON_NULL)
public class ProfessionalDAO {

  
    @Min(value=1, message = "Professional id must be greater than or equal to 1.")
    private long id;

    
    private String name;
    private String phone;
    private String email;
    private String comments;
    private Boolean active;

    public ProfessionalDAO(long id, String name, String phone, String email, String comments, Boolean active) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comments = comments;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProfessionalDAO other = (ProfessionalDAO) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
