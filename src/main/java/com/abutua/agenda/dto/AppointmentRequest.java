package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record AppointmentRequest(
        @FutureOrPresent(message = "A data deve ser maior ou igual a data corrente.") 
        LocalDate date,
        
        LocalTime startTime,
        
        LocalTime endTime,
        
        @NotNull(message = "Area não pode ser nula.")
        @Valid 
        IntegerIdDTO area,
        
        @NotNull(message = "Cliente não pode ser nulo.") 
        @Valid 
        LongIdDTO client,
        
        @NotNull(message = "Profissonal não pode ser nulo.") 
        @Valid 
        LongIdDTO professional,
        
        @NotNull(message = "Tipo do atendimento não pode ser nulo.") 
        @Valid 
        IntegerIdDTO type,
        
        String comments

) {

    
}
