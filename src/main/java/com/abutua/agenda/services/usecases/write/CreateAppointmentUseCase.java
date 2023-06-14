package com.abutua.agenda.services.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dto.AppointmentRequestDTO;
import com.abutua.agenda.dto.TimeSlotResponseDTO;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ClientRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.services.usecases.read.ListProfessionalAvailabilityTimesUseCase;


 // @formatter:off
@Service
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ListProfessionalAvailabilityTimesUseCase listProfessionalAvailabilityTimesUseCase;

    
    public Appointment executeUseCase(AppointmentRequestDTO appointmentSaveDAO) {
        
        checkIfAreaExistsOrThrowsException(appointmentSaveDAO.area().id());

        checkIfAppointmentTypeExistsOrThrowsException(appointmentSaveDAO.type().id());

        Client client = getClientIfExistsOrThrowsException(appointmentSaveDAO.client().id());
        
        checkIfClientHasDateAndTimeAvailableOrThrowsException(
                                                             client,
                                                             appointmentSaveDAO.date(),
                                                             appointmentSaveDAO.startTime(),
                                                             appointmentSaveDAO.endTime()
                                                             );

        Professional professional = getProfessionalIfExistsOrThrowsException(appointmentSaveDAO.professional().id());
        
        checkIfProfessionalIsActiveOrThrowsException(professional);

        checkIfProfessionalHasDateAndTimeAvaliableOrThrowsException(professional, 
                                                                    appointmentSaveDAO.date(),
                                                                    appointmentSaveDAO.startTime(),
                                                                    appointmentSaveDAO.endTime()
                                                                   );

        checkProfessionalAvaliableScheduleOrThrowsException(professional, 
                                                            appointmentSaveDAO.date(),
                                                            appointmentSaveDAO.startTime(), 
                                                            appointmentSaveDAO.endTime()
                                                           );
                                                           
        return appointmentRepository.save(appointmentSaveDAO.toEntity());
    }



    private void checkProfessionalAvaliableScheduleOrThrowsException(Professional professional, 
                                                                     LocalDate date,
                                                                     LocalTime startTime, 
                                                                     LocalTime endTime) {

        List<TimeSlotResponseDTO> slots = listProfessionalAvailabilityTimesUseCase.executeUseCase(date, professional.getId());

        if (slots.isEmpty()) {
            // The professional does not work in the day of week
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"O professional não trabalha no dia da semana selecionado.");
        } else {
            // The start and end time belongs a valid slot?
            var slot = slots.stream().filter(s -> s.startTime().equals(startTime) &&  s.endTime().equals(endTime))
                                                   .findFirst();

            if(slot.isEmpty()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O horário selecionado não corresponde a um horário disponível.");
            }         
        }
    }

    private void checkIfProfessionalHasDateAndTimeAvaliableOrThrowsException(Professional professional, LocalDate date,
            LocalTime startTime, LocalTime endTime) {
        if (appointmentRepository.existsAppointmentForProfessional(professional, date, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "O profesional já possui um agendamento para a data e horário selecionado.");
        }
    }

    private void checkIfProfessionalIsActiveOrThrowsException(Professional professional) {
        if (!professional.getActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Professional não está ativo.");
        }
    }

    private Professional getProfessionalIfExistsOrThrowsException(Long professionalId) {
        return professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional não encontradao."));
    }

    private void checkIfClientHasDateAndTimeAvailableOrThrowsException(Client client, LocalDate date,
            LocalTime startTime, LocalTime endTime) {
        if (appointmentRepository.existsAppointmentForClient(client, date, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cliente já possui um agendamento para o dia e horário selecionado.");
        }
    }

    private Client getClientIfExistsOrThrowsException(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    private void checkIfAppointmentTypeExistsOrThrowsException(Integer typeId) {
        var apppointment = appointmentTypeRepository.findById(typeId);

        if(apppointment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de consulta não encontrada.");
        }
            
    }

    private void checkIfAreaExistsOrThrowsException(Integer areaId) {
        var area = areaRepository.findById(areaId);
        if(area.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Área não encontrada.");
        }
    }
}
// @formatter:on
