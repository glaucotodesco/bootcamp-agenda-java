package com.abutua.agenda.services;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abutua.agenda.dao.AppointmentDAO;
import com.abutua.agenda.dao.AppointmentSaveDAO;
import com.abutua.agenda.dao.AppointmentTypeDAO;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.usecases.write.CreateAppointmentUseCase;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;
    

    public List<AppointmentTypeDAO> getAllTypes() {
        return appointmentTypeRepository.findAll()
                .stream()
                .map(a -> a.toDAO())
                .collect(Collectors.toList());
    }


    public AppointmentDAO save(AppointmentSaveDAO appointmentSaveDAO) {
        Appointment newAppointment = createAppointmentUseCase.executeUseCase(appointmentSaveDAO);
        return newAppointment.toDAO();
    }

}
