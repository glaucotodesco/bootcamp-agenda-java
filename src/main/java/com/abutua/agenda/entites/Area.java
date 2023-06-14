package com.abutua.agenda.entites;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.abutua.agenda.dto.AreaResponseDTO;
import com.abutua.agenda.dto.AreaWithProfessionalsResponseDTO;
import com.abutua.agenda.dto.ProfessionalResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tbl_Area")
public class Area implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40)
    private String name;

    @ManyToMany
    @JoinTable(name = "Tbl_Area_Professional", joinColumns = @JoinColumn(name = "area_id"), inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private Set<Professional> professionals = new HashSet<Professional>();

    public Area() {
    }

    public Area(int id) {
        this.id = id;
    }

    public Area(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AreaWithProfessionalsResponseDTO toDTOWithProfessionals() {
        List<ProfessionalResponseDTO> professionals = this.professionals.stream()
                .map(p -> new ProfessionalResponseDTO(p.getId(), p.getName(), p.getPhone(), p.getActive()))
                .collect(Collectors.toList());

        return new AreaWithProfessionalsResponseDTO(id, name, professionals);
    }

    public Set<Professional> getProfessionals() {
        return professionals;
    }

    public AreaResponseDTO toDTO() {
        AreaResponseDTO dto = new AreaResponseDTO(id, name);
        return dto;
    }

    @Override
    public String toString() {
        return "Area [id=" + id + ", name=" + name + "]";
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
        Area other = (Area) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
