package com.abutua.agenda.entites.runners;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;


@Component
@Order(4)
@Profile("runner")
public class DBRunner04Professional implements ApplicationRunner {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AreaRepository areaRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception { 
        Professional p1= new Professional("Marco Nunes", "11 90232322",true);
        Professional p2= new Professional("Marcelo Silva", "13 99921212",true);

        
        Area a1 = areaRepository.findById(1).get();
        Area a2 = areaRepository.findById(2).get();

        p1.addArea(a1);
        p1.addArea(a2);
        p2.addArea(a2);


        p1.addWorkScheduleItem(DayOfWeek.MONDAY,LocalTime.parse("08:00:00"), LocalTime.parse("12:00:00"));
        p1.addWorkScheduleItem(DayOfWeek.THURSDAY,LocalTime.parse("08:00:00"), LocalTime.parse("12:00:00"));
        p1.addWorkScheduleItem(DayOfWeek.WEDNESDAY,LocalTime.parse("14:00:00"), LocalTime.parse("18:00:00"));
        p2.addWorkScheduleItem(DayOfWeek.WEDNESDAY,LocalTime.parse("14:00:00"), LocalTime.parse("18:00:00"));

        professionalRepository.save(p1);
        professionalRepository.save(p2);

    }
    
}
