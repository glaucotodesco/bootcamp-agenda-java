package com.abutua.agenda.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dto.AppointmentSaveDTO;
import com.abutua.agenda.dto.AppointmentTypeDTO;
import com.abutua.agenda.dto.AreaDTO;
import com.abutua.agenda.dto.ClientDTO;
import com.abutua.agenda.dto.ProfessionalDTO;
import com.abutua.agenda.dto.TimeSlotDTO;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ClientRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.usecases.read.ListProfessionalAvailabilityTimesUseCase;


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

    
    public Appointment executeUseCase(AppointmentSaveDTO appointmentSavedto) {
        // Step 1
        checkIfAreaExistsOrThrowsException(appointmentSavedto.area());

        // Step 2
        checkIfAppointmentTypeExistsOrThrowsException(appointmentSavedto.type());

        // Step 3
        Client client = getClientIfExistsOrThrowsException(appointmentSavedto.client());
        
        //Step 4
        checkIfClientHasDateAndTimeAvailableOrThrowsException(
                                                             client,
                                                             appointmentSavedto.date(),
                                                             appointmentSavedto.startTime(),
                                                             appointmentSavedto.endTime()
                                                             );
        // Step 5
        Professional professional = getProfessionalIfExistsOrThrowsException(appointmentSavedto.professional());

        // Step 6
        checkIfProfessionalIsActiveOrThrowsException(professional);

        // Step 7
        checkIfProfessionalHasDateAndTimeAvaliableOrThrowsException(professional, 
                                                                    appointmentSavedto.date(),
                                                                    appointmentSavedto.startTime(),
                                                                    appointmentSavedto.endTime()
                                                                   );
        // Step 8
        checkProfessionalAvaliableScheduleOrThrowsException(professional, 
                                                            appointmentSavedto.date(),
                                                            appointmentSavedto.startTime(), 
                                                            appointmentSavedto.endTime()
                                                           );

        // Step 9
        return appointmentRepository.save(appointmentSavedto.toEntity());
    }



    private void checkProfessionalAvaliableScheduleOrThrowsException(Professional professional, 
                                                                     LocalDate date,
                                                                     LocalTime startTime, 
                                                                     LocalTime endTime) {

        List<TimeSlotDTO> slots = listProfessionalAvailabilityTimesUseCase.executeUseCase(date, professional.getId());

        if (slots.size() == 0) {
            // The professional does not work in the day of week
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "O professional não trabalha no dia da semana selecionado.");
        } else {
            // The start and end time belongs a valid slot?
            slots.stream().filter(s -> s.startTime().equals(startTime) &&
                    s.endTime().equals(endTime))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                            "O horário selecionado não corresponde a um horário disponível."));
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

    private Professional getProfessionalIfExistsOrThrowsException(ProfessionalDTO professional) {
        return professionalRepository.findById(professional.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional não encontradto."));
    }

    private void checkIfClientHasDateAndTimeAvailableOrThrowsException(Client client, LocalDate date,
            LocalTime startTime, LocalTime endTime) {
        if (appointmentRepository.existsAppointmentForClient(client, date, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cliente já possui um agendamento para o dia e horário selecionado.");
        }
    }

    private Client getClientIfExistsOrThrowsException(ClientDTO client) {
        return clientRepository.findById(client.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    private void checkIfAppointmentTypeExistsOrThrowsException(AppointmentTypeDTO type) {
        appointmentTypeRepository.findById(type.id())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de consulta não encontrada."));
    }

    private void checkIfAreaExistsOrThrowsException(AreaDTO area) {
        areaRepository.findById(area.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área não encontrada."));
    }
}
// @formatter:on
