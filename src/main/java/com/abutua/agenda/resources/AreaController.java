package com.abutua.agenda.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.entites.Area;
import com.abutua.agenda.services.AreaService;

@RestController
@RequestMapping("areas")
public class AreaController {
    

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> getAreas(){
        return ResponseEntity.ok().body(areaService.getAreas());
    }

    @GetMapping("{id}")
    public ResponseEntity<Area> getAreas(@PathVariable int id){
        return ResponseEntity.ok().body(areaService.getAreaById(id));
    }

}
