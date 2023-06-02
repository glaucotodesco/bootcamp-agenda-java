package com.abutua.agenda.dao;

import java.util.ArrayList;
import java.util.List;

public class WorkScheduleDAO {
    
    private Long id;
    private String name;
    private List<WorkScheduleItemDAO> workshedule = new ArrayList<WorkScheduleItemDAO>();

 
    public List<WorkScheduleItemDAO> getWorkshedule() {
        return workshedule;
    }


    public void setWorkshedule(List<WorkScheduleItemDAO> workshedule) {
        this.workshedule = workshedule;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
