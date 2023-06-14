package com.abutua.agenda.entites;


import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.dto.AppointmentResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Tbl_Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column( columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDate date;
    
    @Column( columnDefinition = "TIME WITH TIME ZONE")
    private LocalTime startTime;

    @Column( columnDefinition = "TIME WITH TIME ZONE")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.OPEN;

    @ManyToOne
    @JoinColumn(name ="area_id")
    private Area area;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name ="professional_id")
    private Professional professional;
    
    @ManyToOne
    @JoinColumn(name = "appointment_type_id")
    private AppointmentType type;
    
    @Column(length = 1024)
    private String comments;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public AppointmentStatus getStatus() {
        return status;
    }
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Professional getProfessional() {
        return professional;
    }
    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public AppointmentType getType() {
        return type;
    }
    public void setType(AppointmentType type) {
        this.type = type;
    }
    
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public AppointmentResponseDTO toDTO() {
        return new AppointmentResponseDTO(id,date,startTime,endTime,status,client.toDTO(),area.toDTO(),professional.toDTO(),type.toDTO(),comments);
    }
    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }
  
    @Override
    public String toString() {
        return "Appointment [id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
                + ", status=" + status + ", type=" + type + ", comments=" + comments+ "]";
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
        Appointment other = (Appointment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

   
 
  

    
}
