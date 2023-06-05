package com.abutua.agenda.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.AppointmentStatus;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;


public class AppointmentSaveDAO {
    
    
    @FutureOrPresent(message = "Date must be equal to or later than the current date.")
    private LocalDate date;

    private LocalTime startTime;
    
    private LocalTime endTime;
    
    @NotNull(message = "Area can not be null")
    @Valid
    private AreaDAO area;

    @NotNull(message = "Client can not be null")
    @Valid
    private ClientDAO client;
    
    
    @NotNull(message = "Professional can not be null")
    @Valid
    private ProfessionalDAO professional;

    @NotNull(message = "Type can not be null")
    @Valid
    private AppointmentTypeDAO type;
    
    private String comment;
    
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

    public Appointment toEntity(){
        Appointment appointment = new Appointment();

        appointment.setDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setComment(comment);
        appointment.setType(type.toEntity());
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setClient(new Client(client.getId()));
        appointment.setProfessional(new Professional(professional.getId()));
        appointment.setArea(new Area(area.getId()));
        
        
        return appointment;
    }
    public AreaDAO getArea() {
        return area;
    }
    public void setArea(AreaDAO area) {
        this.area = area;
    }
}
