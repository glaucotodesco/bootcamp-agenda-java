package com.abutua.agenda.entites;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private AppointmentStatus status;
    private Client client;
    private Professional professional;
    private AppointmentType type;
    private List<AppointementComment> comments = new ArrayList<AppointementComment>();

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
    public LocalTime getStart() {
        return start;
    }
    public void setStart(LocalTime start) {
        this.start = start;
    }
    public LocalTime getEnd() {
        return end;
    }
    public void setEnd(LocalTime end) {
        this.end = end;
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
    
    public List<AppointementComment> getComments() {
        return comments;
    }

    public void addComment(AppointementComment comment){
        this.comments.add(comment);
    }

    public AppointmentType getType() {
        return type;
    }
    public void setType(AppointmentType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "Appointment [id=" + id + ", date=" + date + ", start=" + start + ", end=" + end + ", status=" + status
                + "]";
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
