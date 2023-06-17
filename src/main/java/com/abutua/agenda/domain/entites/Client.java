package com.abutua.agenda.domain.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "Tbl_Client")
@PrimaryKeyJoinColumn(name = "person_id")
public class Client extends Person {

    private LocalDate dateOfBirth;

 
    public Client(){
    }
    
    public Client(Long id, String name, String phone, LocalDate dateOfBirth) {
        super(id,name, phone);
        this.dateOfBirth = dateOfBirth;
    }

    public Client(String name, String phone, LocalDate dateOfBirth) {
        super(name, phone);
        this.dateOfBirth = dateOfBirth;
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
  
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment){
        this.appointments.add(appointment);
    }
    
   
         
}
