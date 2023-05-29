package com.abutua.agenda.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private LocalDate dateOfBirth;
    private Gender gender;
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
  
       
}
