package com.abutua.agenda.web.resources;

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

import com.abutua.agenda.domain.services.AppointmentService;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;

@RestController
@RequestMapping("appointments")
@CrossOrigin
public class AppointmentController {


    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Validated @RequestBody AppointmentRequest appointmentRequestDTO) {
        var appointmentdto = appointmentService.save(appointmentRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointmentdto.id())
                .toUri();

        return ResponseEntity.created(location).body(appointmentdto);
    }

    
}
