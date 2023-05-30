package com.abutua.agenda.entites.runners;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.AppointmentComment;
import com.abutua.agenda.entites.AppointmentStatus;
import com.abutua.agenda.entites.AppointmentType;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.repositories.ClientRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;

@Component
@Order(10)
public class DBRunnerAppointment implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    
    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Create a appointment do Client 1
        Client c1 = clientRepository.getReferenceById(1L);
        AppointmentType apty1 = appointmentTypeRepository.getReferenceById(1);
        Professional p1 = professionalRepository.getReferenceById(3L);
        

        Appointment ap1 = new Appointment();
        ap1.setClient(c1);
        ap1.setDate(LocalDate.parse("2023-08-01"));
        ap1.setStartTime(LocalTime.parse("10:00:00"));
        ap1.setEndTime(LocalTime.parse("10:30:00"));
        ap1.setType(apty1);
        ap1.setProfessional(p1);
        ap1.addComment(new AppointmentComment("Primeiro atendimento", Instant.now()));

        appointmentRepository.save(ap1);

        // Create a appointment do Client 1
        Client c2 = clientRepository.getReferenceById(2L);
        AppointmentType apty2 = appointmentTypeRepository.getReferenceById(2);
        Professional p2 = professionalRepository.getReferenceById(4L);

        Appointment ap2 = new Appointment();
        ap2.setClient(c2);
        ap2.setDate(LocalDate.parse("2023-10-01"));
        ap2.setStartTime(LocalTime.parse("11:00:00"));
        ap2.setEndTime(LocalTime.parse("11:30:00"));
        ap2.setType(apty2);
        ap2.setProfessional(p2);
        ap2.addComment(new AppointmentComment("Parcelar em 3x", Instant.now()));

        appointmentRepository.save(ap2);

    }

}
