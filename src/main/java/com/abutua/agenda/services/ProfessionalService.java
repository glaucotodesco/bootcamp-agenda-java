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

import com.abutua.agenda.dto.ProfessionalAvailabilityDaysResponseDTO;
import com.abutua.agenda.dto.ProfessionalResponseDTO;
import com.abutua.agenda.dto.ProfessionalRequestDTO;
import com.abutua.agenda.dto.ProfessionalWithAreasResponseDTO;
import com.abutua.agenda.dto.TimeSlotResponseDTO;
import com.abutua.agenda.dto.WorkScheduleResponseDTO;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.services.exceptions.DatabaseException;
import com.abutua.agenda.services.usecases.read.ListProfessionalAvailabilityDaysUseCase;
import com.abutua.agenda.services.usecases.read.ListProfessionalAvailabilityTimesUseCase;

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

    public ProfessionalWithAreasResponseDTO getById(long id) {
        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional com id = {"+ id + "} não encontrado"));

        return professional.toDTOWithAreas();
    }

    public List<ProfessionalResponseDTO> getAll() {
        return professionalRepository.findAll()
                .stream()
                .map(p -> p.toDTO())
                .collect(Collectors.toList());
    }

    public ProfessionalResponseDTO save(ProfessionalRequestDTO professionalSavedto) {
        Professional professional;
        try {
            professional = professionalRepository.save(professionalSavedto.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao salvar. Dicas: Verefique se a área é válida ou se o email é único",
                    HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Profissional é requirido", HttpStatus.BAD_REQUEST);
        }
        return professional.toDTO();
    }

    public void deleteById(long id) {
        try {
            if (professionalRepository.existsById(id))
                professionalRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Profissional com id = {"+ id + "} não encontrado");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao remover o professional do banco de dados", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(long id, ProfessionalRequestDTO professionalSavedto) {
        try {
            var professional = professionalRepository.getReferenceById(id);

            var areas = areaRepository.findAllById(professionalSavedto.getAreasId());

            if (areas.size() != professionalSavedto.areas().size()) {
                throw new DatabaseException("Área com id={"+ id +"} não existe", HttpStatus.CONFLICT);
            } else {
                professional.setName(professionalSavedto.name());
                professional.setPhone(professionalSavedto.phone());
                professional.setActive(professionalSavedto.active());
                professional.getAreas().clear();
                professional.getAreas().addAll(professionalSavedto.toEntity().getAreas());
                professionalRepository.save(professional);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Profissional não cadastrado!");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Área é requirida!", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao salvar. Dicas: Verefique se a área é válida ou se o email é único",
                    HttpStatus.CONFLICT);
        }
    }

    public WorkScheduleResponseDTO getWorkSchedule(long id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Profissional com id = {"+ id + "} não encontrado"));

        return professional.toWorkScheduledto();
    }

    public ProfessionalAvailabilityDaysResponseDTO getAvailableDaysInMonth(long professionalId, int month, int year) {
        List<Integer> availiabilyDays = listProfessionalAvailabilityUseCase.executeUseCase(professionalId, month, year);
        ProfessionalAvailabilityDaysResponseDTO dto = new ProfessionalAvailabilityDaysResponseDTO(month, year, availiabilyDays);
        return dto;
    }

    public List<TimeSlotResponseDTO> getAvailableTimeSlots(LocalDate date, Long id) {
        return listProfessionalAvailabilityTimesUseCase.executeUseCase(date, id);
    }

}
