package com.abutua.agenda.services;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.entites.Area;
import com.abutua.agenda.repositories.AreaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AreaService {
    
    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAreas(){
        return areaRepository.findAll();
    }

    public Area getAreaById(int id) {
        return areaRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Area not found"));
    }

}
