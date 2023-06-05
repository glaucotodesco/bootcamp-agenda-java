package com.abutua.agenda.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;



@JsonInclude(Include.NON_NULL)
public class AreaDAO {

    @Min(value=1, message = "Area id must be greater than or equal to 1.")
    private int id;

    private String name;
    

    public AreaDAO(int id, String name) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        AreaDAO other = (AreaDAO) obj;
        if (id != other.id)
            return false;
        return true;
    }

    

}
