package com.abutua.agenda.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dao.AppointmentDAO;
import com.abutua.agenda.dao.AppointmentSaveDAO;
import com.abutua.agenda.dao.AppointmentTypeDAO;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AppointmentTypeRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<AppointmentTypeDAO> getAllTypes() {
        return appointmentTypeRepository.findAll()
                .stream()
                .map(a -> a.toDAO())
                .collect(Collectors.toList());
    }

    public AppointmentDAO save(AppointmentSaveDAO appointmentSaveDAO) {
        Appointment appointment = appointmentRepository.save(appointmentSaveDAO.toEntity());
        return appointment.toDAO();
    }

}
