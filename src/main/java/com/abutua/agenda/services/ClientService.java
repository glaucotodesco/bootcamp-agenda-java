package com.abutua.agenda.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dao.ClientDAO;
import com.abutua.agenda.repositories.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDAO> findByNameContaining(String name,int limit){
        Pageable pageable = PageRequest.of(0, 10);
        return clientRepository.findByNameContainingIgnoreCase(name, pageable)
        .stream()
        .map(c -> c.toDAO())
        .collect(Collectors.toList());
    }
    
}
