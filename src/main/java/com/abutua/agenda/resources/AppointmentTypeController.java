package com.abutua.agenda.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.dao.AppointmentTypeDAO;
import com.abutua.agenda.services.AppointmentService;

@RestController
@CrossOrigin
@RequestMapping("appointment-types")
public class AppointmentTypeController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentTypeDAO>> getAppointmentTypes(){
        return ResponseEntity.ok().body(appointmentService.getAllTypes());
    }
    
}
