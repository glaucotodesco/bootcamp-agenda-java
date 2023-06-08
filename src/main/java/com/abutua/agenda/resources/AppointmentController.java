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

import com.abutua.agenda.dto.AppointmentDTO;
import com.abutua.agenda.dto.AppointmentSaveDTO;
import com.abutua.agenda.services.AppointmentService;

@RestController
@RequestMapping("appointments")
@CrossOrigin
public class AppointmentController {


    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Validated @RequestBody AppointmentSaveDTO appointmentSavedto) {
        
        AppointmentDTO appointmentdto = appointmentService.save(appointmentSavedto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointmentdto.id())
                .toUri();

        return ResponseEntity.created(location).body(appointmentdto);
    }

    
}
