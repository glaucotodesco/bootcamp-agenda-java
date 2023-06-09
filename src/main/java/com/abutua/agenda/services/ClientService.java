package com.abutua.agenda.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dto.ClientDTO;
import com.abutua.agenda.repositories.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDTO> findByNameContaining(String name, int limit) {
        var pageable = PageRequest.of(0, limit);

        return clientRepository.findByNameContainingIgnoreCase(name, pageable)
                .stream()
                .map(c -> c.toDTO())
                .collect(Collectors.toList());
    }

}
