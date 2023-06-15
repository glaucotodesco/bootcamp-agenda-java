package com.abutua.agenda.domain.services.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabilityTimesUseCase;

import com.abutua.agenda.dto.AppointmentRequestDTO;
import com.abutua.agenda.dto.TimeSlotResponseDTO;


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
    private SearchProfessionalAvailabilityTimesUseCase listProfessionalAvailabilityTimesUseCase;

    
    public Appointment executeUseCase(AppointmentRequestDTO appointmentSaveDAO) {
        
        checkIfAppointmentTypeExistsOrThrowsException(appointmentSaveDAO.type().id());

        Client client = getClientIfExistsOrThrowsException(appointmentSaveDAO.client().id());
        
        checkIfClientHasDateAndTimeIsAvailableOrThrowsException(
                                                             client,
                                                             appointmentSaveDAO.date(),
                                                             appointmentSaveDAO.startTime(),
                                                             appointmentSaveDAO.endTime()
                                                             );

        Professional professional = getProfessionalIfExistsOrThrowsException(appointmentSaveDAO.professional().id());

        checkIfAreaExistsForProfessioanlOrThrowsException(professional.getId(),appointmentSaveDAO.area().id());
  
        checkIfProfessionalIsActiveOrThrowsException(professional);

        checkIfProfessionalHasDateAndTimeIsAvaliableOrThrowsException(professional, 
                                                                    appointmentSaveDAO.date(),
                                                                    appointmentSaveDAO.startTime(),
                                                                    appointmentSaveDAO.endTime()
                                                                   );

        checkProfessionalAvaliableScheduleOrThrowsException(professional, 
                                                            appointmentSaveDAO.date(),
                                                            appointmentSaveDAO.startTime(), 
                                                            appointmentSaveDAO.endTime()
                                                           );

         checkIfAppointmentIsNowOrFutureOrThrowsException(appointmentSaveDAO.date(),
                                                            appointmentSaveDAO.startTime()
                                                            );
                                                           
        return appointmentRepository.save(appointmentSaveDAO.toEntity());
    }



    private void checkIfAppointmentIsNowOrFutureOrThrowsException(LocalDate date, LocalTime startTime) {
        if(startTime.isBefore(LocalTime.now()) && date.equals(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O horário selecionado no passado");
        }
    }



    private void checkIfAreaExistsForProfessioanlOrThrowsException(Long professioanalId, Integer areaId) {
         var area = areaRepository.findAreaByProfessionalId(professioanalId, areaId);

        if(area.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Área não encontrada para o profissional.");
        }
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

    private void checkIfProfessionalHasDateAndTimeIsAvaliableOrThrowsException(Professional professional, LocalDate date,
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

    private void checkIfClientHasDateAndTimeIsAvailableOrThrowsException(Client client, LocalDate date,
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

   
}
// @formatter:on
