package com.abutua.agenda.dto;

import java.util.List;

public record ProfessionalAvailabilityDaysResponse(
                int month,
                int year,
                List<Integer> availabilityDays) {

}
