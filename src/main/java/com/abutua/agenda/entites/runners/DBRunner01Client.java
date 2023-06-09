package com.abutua.agenda.entites.runners;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.abutua.agenda.entites.Client;
import com.abutua.agenda.repositories.ClientRepository;

@Component
@Order(1)
@Profile("runner")
public class DBRunner01Client implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception { 
        Client c1 = new Client("Ana Maria","15 997079576",LocalDate.parse("2000-08-02"));
        Client c2 = new Client("Pedro Silva","15 923233212",LocalDate.parse("1998-01-22"));

        clientRepository.save(c1);
        clientRepository.save(c2);

    }
    
}
