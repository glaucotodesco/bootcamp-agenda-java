package com.abutua.agenda.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tbl_Role")
public class Role {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public RoleName getRole() {
        return role;
    }
    public void setRole(RoleName role) {
        this.role = role;
    }

   
    @Override
    public String toString() {
        return "Role [id=" + id + ", role=" + role + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
    
}
