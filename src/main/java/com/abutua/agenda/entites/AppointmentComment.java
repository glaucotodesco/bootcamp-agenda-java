package com.abutua.agenda.entites;


import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tbl_Appointment_Comment")
public class AppointmentComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024)
    private String comment;

    @Column( columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant moment;

    
    public AppointmentComment() {
    }

    
    public AppointmentComment(String comment, Instant moment) {
        this.comment = comment;
        this.moment = moment;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }


    
    @Override
    public String toString() {
        return "AppointementComment [id=" + id + ", comment=" + comment + ", moment=" + moment + "]";
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
        AppointmentComment other = (AppointmentComment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Instant getMoment() {
        return moment;
    }


    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    

    
}
