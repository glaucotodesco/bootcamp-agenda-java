package com.abutua.agenda.entites.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.abutua.agenda.entites.AppointmentType;
import com.abutua.agenda.repositories.AppointmentTypeRepository;


@Component
@Order(3)
@Profile("runner")
public class DBRunner03AppointmentType implements ApplicationRunner {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception { 
        AppointmentType apty1 = new AppointmentType("Particular");
        AppointmentType apty2 = new AppointmentType("Convênio");
        

        appointmentTypeRepository.save(apty1);
        appointmentTypeRepository.save(apty2);

    }
    
}
