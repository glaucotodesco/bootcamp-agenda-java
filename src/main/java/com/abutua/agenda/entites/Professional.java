package com.abutua.agenda.entites;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "Tbl_Professional")
@PrimaryKeyJoinColumn(name = "person_id")
public class Professional extends Person{
  
    @Column(nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "professional")    
    private List<Appointment> appointments = new ArrayList<Appointment>();

    @ManyToMany
    @JoinTable(
        name = "Tbl_Area_Professional",
        joinColumns = @JoinColumn(name = "professional_id"),
        inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<Area> areas = new ArrayList<Area>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "professional_id")
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
   
    public void addArea(Area area) {
        this.areas.add(area);
    }

    public void addWorkScheduleItem(DayOfWeek dayOfWeek, LocalTime timeStart, LocalTime timeEnd) {
        this.workSchedule.add(new WorkScheduleItem(dayOfWeek,timeStart, timeEnd));
    }

    @Override
    public String toString() {
        return "Professional [active=" + active + "]";
    }

   
    
}
