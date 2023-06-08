package com.abutua.agenda.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.dto.ProfessionalAvailabilityDaysDTO;
import com.abutua.agenda.dto.ProfessionalDTO;
import com.abutua.agenda.dto.ProfessionalSaveDTO;
import com.abutua.agenda.dto.ProfessionalWithAreaDTO;
import com.abutua.agenda.dto.TimeSlotDTO;
import com.abutua.agenda.dto.WorkScheduleDTO;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
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

    public ProfessionalWithAreaDTO getById(long id) {
        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        return professional.toDTOWithAreas();
    }

    public List<ProfessionalDTO> getAll() {
        return professionalRepository.findAll()
                .stream()
                .map(p -> p.toDTO())
                .collect(Collectors.toList());
    }

    public ProfessionalDTO save(ProfessionalSaveDTO professionalSavedto) {
        Professional professional;
        try {
            professional = professionalRepository.save(professionalSavedto.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation. Tips: Check for valid areas id or unique email ",
                    HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id professional is required", HttpStatus.BAD_REQUEST);
        }
        return professional.toDTO();
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

    public void update(long id, ProfessionalSaveDTO professionalSavedto) {
        try {
            Professional professional = professionalRepository.getReferenceById(id);

            List<Area> areas = areaRepository.findAllById(professionalSavedto.getAreasId());

            if (areas.size() != professionalSavedto.areasdto().size()) {
                throw new DatabaseException("Constrain violation, id area doesn't exist", HttpStatus.CONFLICT);
            } else {
                professional.setName(professionalSavedto.name());
                professional.setEmail(professionalSavedto.email());
                professional.setPhone(professionalSavedto.phone());
                professional.setName(professionalSavedto.name());
                professional.setActive(professionalSavedto.active());
                professional.getAreas().clear();
                professional.getAreas().addAll(professionalSavedto.toEntity().getAreas());
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

    public WorkScheduleDTO getWorkSchedule(long id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        return professional.toWorkScheduledto();
    }


 
    public ProfessionalAvailabilityDaysDTO getAvailableDaysInMonth(long professionalId, int month, int year) {
        List<Integer> availiabilyDays = listProfessionalAvailabilityUseCase.executeUseCase(professionalId, month, year);
        ProfessionalAvailabilityDaysDTO dto = new ProfessionalAvailabilityDaysDTO(month,year,availiabilyDays);
        return dto;
    }

    public List<TimeSlotDTO> getAvailableTimeSlots(LocalDate date, Long id) {
        return listProfessionalAvailabilityTimesUseCase.executeUseCase(date, id);
    }

    
}
