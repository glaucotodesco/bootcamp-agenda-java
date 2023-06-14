package com.abutua.agenda.services;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dto.AppointmentResponseDTO;
import com.abutua.agenda.dto.AppointmentRequestDTO;
import com.abutua.agenda.dto.AppointmentTypeResponseDTO;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.services.usecases.write.CreateAppointmentUseCase;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;
    

    public List<AppointmentTypeResponseDTO> getAllTypes() {
        return appointmentTypeRepository.findAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }


    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentSavedto) {
        var newAppointment = createAppointmentUseCase.executeUseCase(appointmentSavedto);
        return newAppointment.toDTO();
    }

}
