package com.abutua.agenda.domain.entites;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.abutua.agenda.dto.AreaResponseDTO;
import com.abutua.agenda.dto.ProfessionalResponseDTO;
import com.abutua.agenda.dto.ProfessionalWithAreasResponseDTO;
import com.abutua.agenda.dto.WorkScheduleItemResponseDTO;
import com.abutua.agenda.dto.WorkScheduleResponseDTO;

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
    private boolean active;

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

    public Professional( String name, String phone, boolean active) {
        super(name, phone);
        this.active = active;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment){
        this.appointments.add(appointment);
    }

   

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    public ProfessionalResponseDTO toDTO() {
        return new ProfessionalResponseDTO(getId(),getName(),getPhone(), isActive());    
    }

    public ProfessionalWithAreasResponseDTO toDTOWithAreas() {
        List<AreaResponseDTO> areas = this.areas.stream()
                         .map( a -> new AreaResponseDTO(a.getId(), a.getName()))
                         .collect(Collectors.toList());
        
        ProfessionalWithAreasResponseDTO dto = new ProfessionalWithAreasResponseDTO(getId(),getName(),getPhone(),active,areas);
        return dto;
    }
   
    public WorkScheduleResponseDTO toWorkScheduledto() {
        List<WorkScheduleItemResponseDTO> workScheduleList = workSchedule.stream().map( wsi -> wsi.toDTO()).collect(Collectors.toList());
        return new WorkScheduleResponseDTO(getId(), getName(),workScheduleList);
    }


    
}