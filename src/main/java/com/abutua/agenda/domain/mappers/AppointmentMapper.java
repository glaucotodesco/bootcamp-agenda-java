package com.abutua.agenda.domain.mappers;

import org.springframework.beans.BeanUtils;

import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.AppointmentType;
import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;

public class AppointmentMapper {
    
    public static Appointment appointmentFromDTO(AppointmentRequest appointmentRequest){
        Appointment appointment = new Appointment();

        BeanUtils.copyProperties(appointmentRequest, appointment);
        appointment.setType(new AppointmentType(appointmentRequest.type().id()));
        appointment.setClient(new Client(appointmentRequest.client().id()));
        appointment.setProfessional(new Professional(appointmentRequest.professional().id()));
        appointment.setArea(new Area(appointmentRequest.area().id()));

        
        return appointment;
    }

    public static AppointmentResponse toAppointmenteResponseDTO(Appointment appointment) {
        return new AppointmentResponse(appointment.getId(), 
                                      appointment.getDate(),
                                      appointment.getStartTime(),
                                      appointment.getEndTime(),
                                      appointment.getStatus(),
                                      ClientMapper.toClientResponseDTO(appointment.getClient()),
                                      AreaMapper.toResponseAreaDTO(appointment.getArea()),
                                      ProfessionalMapper.toResponseProfessionalDTO(appointment.getProfessional()),
                                      AppointmentTypeMapper.toAppointmentTypeResponseDTO(appointment.getType()),
                                      appointment.getComments()
                                      );
    }
}
