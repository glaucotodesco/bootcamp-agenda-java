package com.abutua.agenda.dao;

import java.util.ArrayList;
import java.util.List;


public class ProfessionalAvailabilityDaysDAO {
    private long id;
    private String name;
    private int month;
    private int year;
    private List<Integer> availabilityDays = new ArrayList<Integer>();
  
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Integer> getAvailabilityDays() {
        return availabilityDays;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setAvailabilityDays(List<Integer> availabilityDays) {
        this.availabilityDays = availabilityDays;
    }
    
}
