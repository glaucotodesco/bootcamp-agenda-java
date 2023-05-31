package com.abutua.agenda.entites;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tbl_Work_Schedule_Item")
public class WorkScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;
    
    @Column( columnDefinition = "TIME WITH TIME ZONE")
    private LocalTime starTime;
    
    @Column( columnDefinition = "TIME WITH TIME ZONE")
    private LocalTime endTime;

    
    public WorkScheduleItem() {
    }

    
    public WorkScheduleItem(DayOfWeek dayOfWeek, LocalTime starTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.starTime = starTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public LocalTime getStarTime() {
        return starTime;
    }
    public void setStarTime(LocalTime starTime) {
        this.starTime = starTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return "WorkSchedule [id=" + id + ", dayOfWeek=" + dayOfWeek + ", starTime=" + starTime + ", endTime=" + endTime
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
        WorkScheduleItem other = (WorkScheduleItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
    
}
