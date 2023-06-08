package com.abutua.agenda.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.abutua.agenda.dto.ClientDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "Tbl_Client")
@PrimaryKeyJoinColumn(name = "person_id")
public class Client extends Person {

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Client(){
    }

    public Client(String name, String phone, String email, String comments, LocalDate dateOfBirth, Gender gender) {
        super(name, phone, email, comments);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Client(Long id) {
        setId(id);
    }

    @OneToMany(mappedBy = "client")
    private List<Appointment> appointments = new ArrayList<Appointment>();

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

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment){
        this.appointments.add(appointment);
    }

    @Override
    public String toString() {
        return "Client [dateOfBirth=" + dateOfBirth + ", gender=" + gender + "]";
    }

    public ClientDTO toDTO(){
        return new ClientDTO(getId(),getName(),getPhone(),getEmail(),getComment(),dateOfBirth,gender);
    }
         
}
