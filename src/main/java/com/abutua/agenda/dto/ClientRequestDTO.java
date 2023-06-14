package com.abutua.agenda.dto;

import java.time.LocalDate;
import com.abutua.agenda.entites.Client;
import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(
        
        @NotBlank(message = "Nome requirido")
        String name,

        @NotBlank(message = "Telefone requirido")
        String phone,

        LocalDate dateOfBirth
) {

    public Client toEntity() {
        return new Client(name,phone,dateOfBirth);
    }

}
