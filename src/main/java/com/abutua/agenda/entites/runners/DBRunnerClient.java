package com.abutua.agenda.entites.runners;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Gender;
import com.abutua.agenda.repositories.ClientRepository;

@Component
@Order(1)
public class DBRunnerClient implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception { 
        Client c1 = new Client(1L, "Ana Maria","15 997079576","ana@gmail.com","Cliente Vip",LocalDate.parse("2000-08-02"),Gender.FEMALE);
        Client c2 = new Client(2L, "Pedro Silva","15 923233212","pedro@gmail.com","",LocalDate.parse("1998-01-22"),Gender.MALE);

        clientRepository.save(c1);
        clientRepository.save(c2);

    }
    
}
