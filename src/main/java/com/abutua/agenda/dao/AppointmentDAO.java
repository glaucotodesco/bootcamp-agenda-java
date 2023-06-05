package com.abutua.agenda.dao;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.entites.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class AppointmentDAO {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status = AppointmentStatus.OPEN;
    private ClientDAO client;
    private AreaDAO area;
    private ProfessionalDAO professional;
    private AppointmentTypeDAO type;
    private String comment;

    
    public AppointmentDAO(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, AppointmentStatus status,
            ClientDAO client, ProfessionalDAO professional, AppointmentTypeDAO type, String comment, AreaDAO area) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.client = client;
        this.professional = professional;
        this.type = type;
        this.comment = comment;
        this.area = area;
    }
  
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
    public ClientDAO getClient() {
        return client;
    }
    public void setClient(ClientDAO client) {
        this.client = client;
    }
    public ProfessionalDAO getProfessional() {
        return professional;
    }
    public void setProfessional(ProfessionalDAO professional) {
        this.professional = professional;
    }
    public AppointmentTypeDAO getType() {
        return type;
    }
    public void setType(AppointmentTypeDAO type) {
        this.type = type;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public AreaDAO getArea() {
        return area;
    }

    public void setArea(AreaDAO area) {
        this.area = area;
    }


    
}
