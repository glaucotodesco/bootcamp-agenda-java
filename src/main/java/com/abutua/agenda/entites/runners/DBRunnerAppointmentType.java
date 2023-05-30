package com.abutua.agenda.entites.runners;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.abutua.agenda.entites.AppointmentType;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Gender;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.repositories.ClientRepository;

@Component
@Order(2)
public class DBRunnerAppointmentType implements ApplicationRunner {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception { 
        AppointmentType apty1 = new AppointmentType();
        apty1.setType("Particular");

        AppointmentType apty2 = new AppointmentType();
        apty2.setType("Convênio");

        appointmentTypeRepository.save(apty1);
        appointmentTypeRepository.save(apty2);

    }
    
}
