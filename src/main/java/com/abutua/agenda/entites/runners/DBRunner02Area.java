package com.abutua.agenda.entites.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.abutua.agenda.entites.Area;
import com.abutua.agenda.repositories.AreaRepository;


@Component
@Order(2)
@Profile("runner")
public class DBRunner02Area implements ApplicationRunner {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception { 
        
        Area a1= new Area("Fisioterapia");
        Area a2= new Area("Terapia Ocupacional");

        areaRepository.save(a1);
        areaRepository.save(a2);

    }
    
}
