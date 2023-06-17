package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entites.AppointmentType;
import com.abutua.agenda.dto.AppointmentTypeResponse;

public class AppointmentTypeMapper {
    
    public static AppointmentTypeResponse toAppointmentTypeResponseDTO(AppointmentType type) {
        return new AppointmentTypeResponse(type.getId(), type.getType());
    }


}
