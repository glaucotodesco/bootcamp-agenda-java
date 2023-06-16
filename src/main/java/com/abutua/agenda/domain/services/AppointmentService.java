package com.abutua.agenda.domain.services;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.mappers.AppointmentMapper;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.services.usecases.write.CreateAppointmentUseCase;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;
import com.abutua.agenda.dto.AppointmentTypeResponse;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;
    

    public List<AppointmentTypeResponse> getAllTypes() {
        return appointmentTypeRepository.findAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }


    public AppointmentResponse save(AppointmentRequest appointmentSavedto) {
        var newAppointment = createAppointmentUseCase.executeUseCase(appointmentSavedto);
        return AppointmentMapper.toAppointmenteResponseDTO(newAppointment);
    }

}
