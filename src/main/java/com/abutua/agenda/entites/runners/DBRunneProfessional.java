package com.abutua.agenda.entites.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.ProfessionalRepository;


@Component
@Order(3)
public class DBRunneProfessional implements ApplicationRunner {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception { 
        Professional p1= new Professional("Marco Nunes", "11 90232322","marco@gmail.com","",true);
        Professional p2= new Professional("Marcelo Silva", "13 99921212","marcelo@gmail.com","Só Particular",true);

        professionalRepository.save(p1);
        professionalRepository.save(p2);
    }
    
}
