package com.abutua.agenda.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.dao.AppointmentDAO;
import com.abutua.agenda.dao.AppointmentSaveDAO;
import com.abutua.agenda.dao.AppointmentTypeDAO;
import com.abutua.agenda.dao.TimeSlot;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.AppointmentType;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Client;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.entites.WorkScheduleItem;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AppointmentTypeRepository;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ClientRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.repositories.WorkScheduleItemRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    
    public List<AppointmentTypeDAO> getAllTypes() {
        return appointmentTypeRepository.findAll()
                .stream()
                .map(a -> a.toDAO())
                .collect(Collectors.toList());
    }

    public AppointmentDAO save(AppointmentSaveDAO appointmentSaveDAO) {

        areaRepository.findById(appointmentSaveDAO.getArea().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area id not found"));

        appointmentTypeRepository.findById(appointmentSaveDAO.getType().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Type id not found"));

        
        Client client = clientRepository.findById(appointmentSaveDAO.getClient().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client id not found"));

        if(appointmentRepository.existsAppointmentForClient(client,appointmentSaveDAO.getDate(), appointmentSaveDAO.getStartTime(), appointmentSaveDAO.getEndTime())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Client has already been reserved for the specified date and time");
        }

        Professional professional = professionalRepository.findById(appointmentSaveDAO.getProfessional().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional id not found"));

        if(!professional.getActive()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Professional id is not active");
        }

        //Is Date and time available?
        if(appointmentRepository.existsAppointmentForProfessional(  professional, 
                                                                    appointmentSaveDAO.getDate(),
                                                                    appointmentSaveDAO.getStartTime(), 
                                                                    appointmentSaveDAO.getEndTime())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Professional has already been booked or reserved for the specified date and time");
        }

        
        List<TimeSlot> slots = professionalService.getAvailableTimeSlots(appointmentSaveDAO.getDate(), professional.getId());
        
        if(slots.size() == 0){
            //The professional does not work in the day of week
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Professional does not work this day of week");
        }
        else{
            //The start and end time belongs a valid slot?
            slots.stream().filter(s -> s.getStartTime().equals(appointmentSaveDAO.getStartTime()) &&
                                   s.getEndTime().equals(appointmentSaveDAO.getEndTime())
                             )
                             .findFirst()
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Time slot for this day of week does not exist"));
        } 
        


        Appointment appointment = appointmentRepository.save(appointmentSaveDAO.toEntity());
        return appointment.toDAO();
    }

}
