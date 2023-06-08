package com.abutua.agenda.dto;

import java.util.List;

public record ProfessionalAvailabilityDaysDTO(
                int month,
                int year,
                List<Integer> availabilityDays) {

}
