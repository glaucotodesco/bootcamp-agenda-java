package com.abutua.agenda.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dao.AppointmentTypeDAO;
import com.abutua.agenda.dao.AreaDAO;
import com.abutua.agenda.dao.ClientDAO;
import com.abutua.agenda.dao.ProfessionalDAO;
import com.abutua.agenda.dao.TimeSlot;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.dao.AppointmentSaveDAO;
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

    
    public Appointment executeUseCase(AppointmentSaveDAO appointmentSaveDAO) {
        // Step 1
        checkIfAreaExistsOrThrowsException(appointmentSaveDAO.getArea());

        // Step 2
        checkIfAppointmentTypeExistsOrThrowsException(appointmentSaveDAO.getType());

        // Step 3
        Client client = getClientIfExistsOrThrowsException(appointmentSaveDAO.getClient());
        
        //Step 4
        checkIfClientHasDateAndTimeAvailableOrThrowsException(
                                                             client,
                                                             appointmentSaveDAO.getDate(),
                                                             appointmentSaveDAO.getStartTime(),
                                                             appointmentSaveDAO.getEndTime()
                                                             );
        // Step 5
        Professional professional = getProfessionalIfExistsOrThrowsException(appointmentSaveDAO.getProfessional());

        // Step 6
        checkIfProfessionalIsActiveOrThrowsException(professional);

        // Step 7
        checkIfProfessionalHasDateAndTimeAvaliableOrThrowsException(professional, 
                                                                    appointmentSaveDAO.getDate(),
                                                                    appointmentSaveDAO.getStartTime(),
                                                                    appointmentSaveDAO.getEndTime()
                                                                   );
        // Step 8
        checkProfessionalAvaliableScheduleOrThrowsException(professional, 
                                                            appointmentSaveDAO.getDate(),
                                                            appointmentSaveDAO.getStartTime(), 
                                                            appointmentSaveDAO.getEndTime()
                                                           );

        // Step 9
        return appointmentRepository.save(appointmentSaveDAO.toEntity());
    }



    private void checkProfessionalAvaliableScheduleOrThrowsException(Professional professional, 
                                                                     LocalDate date,
                                                                     LocalTime startTime, 
                                                                     LocalTime endTime) {

        List<TimeSlot> slots = listProfessionalAvailabilityTimesUseCase.executeUseCase(date, professional.getId());

        if (slots.size() == 0) {
            // The professional does not work in the day of week
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "O professional não trabalha no dia da semana selecionado.");
        } else {
            // The start and end time belongs a valid slot?
            slots.stream().filter(s -> s.getStartTime().equals(startTime) &&
                    s.getEndTime().equals(endTime))
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

    private Professional getProfessionalIfExistsOrThrowsException(ProfessionalDAO professional) {
        return professionalRepository.findById(professional.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional não encontradao."));
    }

    private void checkIfClientHasDateAndTimeAvailableOrThrowsException(Client client, LocalDate date,
            LocalTime startTime, LocalTime endTime) {
        if (appointmentRepository.existsAppointmentForClient(client, date, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cliente já possui um agendamento para o dia e horário selecionado.");
        }
    }

    private Client getClientIfExistsOrThrowsException(ClientDAO client) {
        return clientRepository.findById(client.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    private void checkIfAppointmentTypeExistsOrThrowsException(AppointmentTypeDAO type) {
        appointmentTypeRepository.findById(type.getId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de consulta não encontrada."));
    }

    private void checkIfAreaExistsOrThrowsException(AreaDAO area) {
        areaRepository.findById(area.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área não encontrada."));
    }
}
// @formatter:on
