package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.dto.ClientResponse;

public class ClientMapper {
    
    public static ClientResponse toClientResponseDTO(Client client) {
        return new ClientResponse(client.getId(),client.getName(),client.getPhone(),client.getDateOfBirth());
    }
}
