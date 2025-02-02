package com.abutua.agenda.domain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.mappers.ClientMapper;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.services.exceptions.DatabaseException;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientResponse> findByNameContaining(String name, int limit, int page) {
       var pageable = PageRequest.of(page, limit);
       Page<Client> pageClient = clientRepository.findByNameContainingIgnoreCase(name, pageable);
       return   pageClient.map(c -> ClientMapper.toClientResponseDTO(c));
    }

    public ClientResponse getById(long id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente com id={" + id + "} não encontrado."));

        return ClientMapper.toClientResponseDTO(client);
    }

    public void deleteById(long id) {
        try {
            if (clientRepository.existsById(id))
                clientRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Cliente com id={" + id + "} não encontrado.");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Conflito ao remover o cliente", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(long id, ClientRequest clientSaveDTO) {
        try {
            var client = clientRepository.getReferenceById(id);

            client.setName(clientSaveDTO.name());
            client.setPhone(clientSaveDTO.phone());
            client.setDateOfBirth(clientSaveDTO.dateOfBirth());

            clientRepository.save(client);

        }
        catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Cliente com id={" + id + "} não encontrado.");
        } 
    }

    public ClientResponse save(ClientRequest clientRequest) {
        Client client = clientRepository.save(ClientMapper.clientFromDTO(clientRequest));
        return ClientMapper.toClientResponseDTO(client);
    }

}
