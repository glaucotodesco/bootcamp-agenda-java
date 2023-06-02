package com.abutua.agenda.dao;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkScheduleItemDAO {
    private long id;
    private DayOfWeek dayOfWeek;
    private LocalTime starTime;
    private LocalTime endTime;

    public WorkScheduleItemDAO(Long id, DayOfWeek dayOfWeek, LocalTime starTime, LocalTime endTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.starTime = starTime;
        this.endTime = endTime;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
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




}
