package com.abutua.agenda.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.dao.AppointmentDAO;
import com.abutua.agenda.dao.AppointmentSaveDAO;
import com.abutua.agenda.services.AppointmentService;

@RestController
@RequestMapping("appointments")
@CrossOrigin
public class AppointmentController {


    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDAO> createAppointment(@Validated @RequestBody AppointmentSaveDAO appointmentSaveDAO) {
        
        AppointmentDAO appointmentDAO = appointmentService.save(appointmentSaveDAO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointmentDAO.getId())
                .toUri();

        return ResponseEntity.created(location).body(appointmentDAO);
    }

    
}
