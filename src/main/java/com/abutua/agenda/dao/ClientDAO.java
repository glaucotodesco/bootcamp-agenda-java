package com.abutua.agenda.dao;

import java.time.LocalDate;

import com.abutua.agenda.entites.Gender;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;


@JsonInclude(Include.NON_NULL)
public class ClientDAO {

    @Min(value=1, message = "Client id must be greater than or equal to 1.")
    private long id;
    
    private String name;
    private String phone;
    private String email;
    private String comment;
    private LocalDate dateOfBirth;
    private Gender gender;
    
    public ClientDAO(long id, String name, String phone, String email, String comment, LocalDate dateOfBirth,
            Gender gender) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
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
        return comment;
    }
    public void setComments(String comments) {
        this.comment = comments;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    
}
