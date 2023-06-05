package com.abutua.agenda.entites;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.abutua.agenda.dao.AreaDAO;
import com.abutua.agenda.dao.ProfessionalDAO;
import com.abutua.agenda.dao.ProfessionalWithAreaDAO;
import com.abutua.agenda.dao.WorkScheduleDAO;

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
    private Set<Area> areas = new HashSet<Area>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professional")
    private List<WorkScheduleItem> workSchedule = new ArrayList<WorkScheduleItem>();

    public Professional(Long id){
        super(id);
    }

    public Professional(){
    }

    public Professional( String name, String phone, String email, String comments, boolean active) {
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

    public Set<Area> getAreas() {
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

    public ProfessionalDAO toDAO() {
        return new ProfessionalDAO(getId(),getName(),getPhone(),getEmail(),getComment(), getActive());    
    }

    public ProfessionalWithAreaDAO toDAOWithAreas() {
        ProfessionalWithAreaDAO dao = new ProfessionalWithAreaDAO(getId(),getName(),getPhone(),getEmail(),getComment(), getActive());

        dao.setAreas(
            areas.stream()
                         .map( a -> new AreaDAO(a.getId(), a.getName()))
                         .collect(Collectors.toList()));
        
        return dao;
    }
   
    public WorkScheduleDAO toWorkScheduleDAO() {
        WorkScheduleDAO workScheduleDAO = new WorkScheduleDAO();

        workScheduleDAO.setId(getId());
        workScheduleDAO.setName(getName());
        workScheduleDAO.setWorkshedule(workSchedule.stream().map( wsi -> wsi.toDAO()).collect(Collectors.toList()));

        return workScheduleDAO;
    }


    
}
