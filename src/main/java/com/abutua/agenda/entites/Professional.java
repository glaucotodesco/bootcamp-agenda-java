package com.abutua.agenda.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Transient;

@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Professional extends Person{
  
    private Boolean active;

    @OneToMany(mappedBy = "professional")    
    private List<Appointment> appointments = new ArrayList<Appointment>();

    @Transient
    private List<Area> areas = new ArrayList<Area>();

    @Transient
    private List<WorkScheduleItem> workSchedule = new ArrayList<WorkScheduleItem>();

    public Professional(){
    }

    public Professional( String name, String phone, String email, String comments, Boolean active) {
        super(name, phone, email, comments);
        this.active = active;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment){
        this.appointments.add(appointment);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Area> getAreas() {
        return areas;
    }
   
    public List<WorkScheduleItem> getWorkSchedule() {
        return workSchedule;
    }
    public void addWorkSchedule(WorkScheduleItem workScheduleItem){
        this.workSchedule.add(workScheduleItem);
    }

    @Override
    public String toString() {
        return "Professional [active=" + active + "]";
    }
    
}
