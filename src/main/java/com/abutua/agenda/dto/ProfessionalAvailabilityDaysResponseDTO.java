package com.abutua.agenda.dto;

import java.util.List;

public record ProfessionalAvailabilityDaysResponseDTO(
                int month,
                int year,
                List<Integer> availabilityDays) {

}
