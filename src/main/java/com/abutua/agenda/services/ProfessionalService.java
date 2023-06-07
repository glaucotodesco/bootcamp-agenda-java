package com.abutua.agenda.services;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.abutua.agenda.dao.ProfessionalAvailabilityDaysDAO;
import com.abutua.agenda.dao.ProfessionalDAO;
import com.abutua.agenda.dao.ProfessionalSaveDAO;
import com.abutua.agenda.dao.ProfessionalWithAreaDAO;
import com.abutua.agenda.dao.TimeSlot;
import com.abutua.agenda.dao.WorkScheduleDAO;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.entites.ProfessionalScheduleDays;
import com.abutua.agenda.entites.ProfessionalWeekDaysSlot;
import com.abutua.agenda.entites.WorkScheduleItem;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.repositories.WorkScheduleItemRepository;
import com.abutua.agenda.resources.exceptions.ParameterException;
import com.abutua.agenda.services.exceptions.DatabaseException;
import com.abutua.agenda.usecases.read.ListProfessionalAvailabilityDaysUseCase;
import com.abutua.agenda.usecases.read.ListProfessionalAvailabilityTimesUseCase;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;
    
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ListProfessionalAvailabilityTimesUseCase listProfessionalAvailabilityTimesUseCase;
 

   


    @Autowired
    private ListProfessionalAvailabilityDaysUseCase listProfessionalAvailabilityUseCase;

    public ProfessionalWithAreaDAO getById(long id) {
        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        return professional.toDAOWithAreas();
    }

    public List<ProfessionalDAO> getAll() {
        return professionalRepository.findAll()
                .stream()
                .map(p -> p.toDAO())
                .collect(Collectors.toList());
    }

    public ProfessionalDAO save(ProfessionalSaveDAO professionalSaveDAO) {
        Professional professional;
        try {
            professional = professionalRepository.save(professionalSaveDAO.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation. Tips: Check for valid areas id or unique email ",
                    HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id professional is required", HttpStatus.BAD_REQUEST);
        }
        return professional.toDAO();
    }

    public void deleteById(long id) {
        try {
            if (professionalRepository.existsById(id))
                professionalRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Professional not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, professional can't delete", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(long id, ProfessionalSaveDAO professionalSaveDAO) {
        try {
            Professional professional = professionalRepository.getReferenceById(id);

            List<Area> areas = areaRepository.findAllById(professionalSaveDAO.getAreasId());

            if (areas.size() != professionalSaveDAO.getAreas().size()) {
                throw new DatabaseException("Constrain violation, id area doesn't exist", HttpStatus.CONFLICT);
            } else {
                professional.setName(professionalSaveDAO.getName());
                professional.setEmail(professionalSaveDAO.getEmail());
                professional.setPhone(professionalSaveDAO.getPhone());
                professional.setName(professionalSaveDAO.getName());
                professional.setActive(professionalSaveDAO.isActive());
                professional.getAreas().clear();
                professional.getAreas().addAll(professionalSaveDAO.toEntity().getAreas());
                professionalRepository.save(professional);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Professional not found");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id area is required", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation. Tips: Check for valid areas id or unique email ",
                    HttpStatus.CONFLICT);
        }
    }

    public WorkScheduleDAO getWorkSchedule(long id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        return professional.toWorkScheduleDAO();
    }


 
    public ProfessionalAvailabilityDaysDAO getAvailableDaysInMonth(long professionalId, int month, int year) {

        List<Integer> availiabilyDays = listProfessionalAvailabilityUseCase.executeUseCase(professionalId, month, year);

        ProfessionalAvailabilityDaysDAO dao = new ProfessionalAvailabilityDaysDAO();
        dao.setMonth(month);
        dao.setYear(year);
        dao.setAvailabilityDays(availiabilyDays);

        return dao;
    }

    public List<TimeSlot> getAvailableTimeSlots(LocalDate date, Long id) {
        return listProfessionalAvailabilityTimesUseCase.executeUseCase(date, id);
    }

    
}
