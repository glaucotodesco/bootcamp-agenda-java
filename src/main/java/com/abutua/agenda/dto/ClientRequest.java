package com.abutua.agenda.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record ClientRequest(
        
        @NotBlank(message = "Nome requirido")
        String name,

        @NotBlank(message = "Telefone requirido")
        String phone,

        LocalDate dateOfBirth
) {

   

}
