package com.abutua.agenda.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfessionalSaveDAO {

    @NotBlank(message = "Name can not be blank")
    @Size(min = 3, max = 40, message = "Name length min=3 and max=40")
    private String name;

    @Size(max=40)
    private String phone;

    @Email
    @Size(max=40)
    private String email;

    @Size(max=1024)
    private String comments;

    private boolean active;


    private Set<AreaDAO> areasDAO = new HashSet<AreaDAO>();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Professional toEntity() {
        Professional professional = new Professional(name,phone,email,comments, active);

        professional.getAreas().addAll(areasDAO
                                        .stream()
                                        .map(a -> new Area(a.getId()))
                                        .collect(Collectors.toList()));
        return professional;
    }


    public Set<AreaDAO> getAreas() {
        return areasDAO;
    }

    public void setAreas(Set<AreaDAO> areas) {
        this.areasDAO = areas;
    }


    public Iterable<Integer> getAreasId() {
        return areasDAO.stream().map( a -> Integer.valueOf(a.getId())).collect(Collectors.toList());
    }
}
