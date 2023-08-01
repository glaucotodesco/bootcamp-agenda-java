package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.mappers.ProfessionalMapper;
import com.abutua.agenda.domain.mappers.WorkScheduleMapper;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.DatabaseException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabilityDaysUseCase;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabilityTimesUseCase;
import com.abutua.agenda.dto.ProfessionalRequest;
import com.abutua.agenda.dto.ProfessionalResponse;
import com.abutua.agenda.dto.ProfessionalWithAreasResponse;
import com.abutua.agenda.dto.TimeSlotResponse;
import com.abutua.agenda.dto.WorkScheduleResponse;
import com.abutua.agenda.web.resources.exceptions.ParameterException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private SearchProfessionalAvailabilityTimesUseCase listProfessionalAvailabilityTimesUseCase;

    @Autowired
    private SearchProfessionalAvailabilityDaysUseCase listProfessionalAvailabilityUseCase;

    public ProfessionalWithAreasResponse getById(long id) {
        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Profissional com id = {" + id + "} não encontrado"));

        return ProfessionalMapper.toResponseProfessionalWithAreasDTO(professional);
    }

    public List<ProfessionalResponse> getAll() {
        return professionalRepository.findAll()
                .stream()
                .map(p -> ProfessionalMapper.toResponseProfessionalDTO(p))
                .collect(Collectors.toList());
    }

    public ProfessionalResponse save(ProfessionalRequest professionalRequest) {
        Professional professional;
        try {
            professional = professionalRepository.save(ProfessionalMapper.professionalFromDTO(professionalRequest));
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao salvar. Verifique se a área é válida.",
                    HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Profissional é requirido", HttpStatus.BAD_REQUEST);
        }
        return ProfessionalMapper.toResponseProfessionalDTO(professional);
    }

    public void deleteById(long id) {
        try {
            if (professionalRepository.existsById(id))
                professionalRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Profissional com id = {" + id + "} não encontrado");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao remover o professional do banco de dados", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(long id, ProfessionalRequest professionalRequest) {
        try {
            var professional = professionalRepository.getReferenceById(id);

            var areas = areaRepository.findAllById(professionalRequest.getAreasId());

            if (areas.size() != professionalRequest.areas().size()) {
                throw new DatabaseException("Área com id={" + id + "} não existe", HttpStatus.CONFLICT);
            } else {
                ProfessionalMapper.updateProfessional(professional, professionalRequest);
                professionalRepository.save(professional);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Profissional não cadastrado!");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Área é requirida!", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao salvar. Dicas: Verifique se a área é válida",
                    HttpStatus.CONFLICT);
        }
    }

    public WorkScheduleResponse getWorkSchedule(long id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Profissional com id = {" + id + "} não encontrado"));

        return WorkScheduleMapper.toWorkScheduledDTO(professional);
    }

    public List<Integer> getAvailableDaysInMonth(long professionalId, int month, int year) {

        checkIfMonthIsValidOrThrowsException(month);
        checkIfYearIsValidOrThrowsException(year);
        checkIfMonthAndYearIsValidOrThrowsException(month, year);
        checkProfessionalExistsOrThrowsException(professionalId);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return listProfessionalAvailabilityUseCase.executeUseCase(professionalId, start, end);
    }

    public List<TimeSlotResponse> getAvailableTimeSlots(LocalDate date, Long id) {
        return listProfessionalAvailabilityTimesUseCase.executeUseCase(date, id);
    }

    private void checkIfMonthAndYearIsValidOrThrowsException(int month, int year) {
        if (month < LocalDate.now().getMonthValue() && year == LocalDate.now().getYear()) {
            throw new ParameterException(
                    "Valor para o mês inválido. Mês e ano deve ser maior ou igual a data corrente.");
        }
    }

    private void checkIfYearIsValidOrThrowsException(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new ParameterException("Valor para o ano inválido. O ano deve ser maior ou igual ao ano corrente.");
        }
    }

    private void checkIfMonthIsValidOrThrowsException(int month) {
        if (month < 1 || month > 12) {
            throw new ParameterException("Valor para o mês inválido.");
        }
    }

    private void checkProfessionalExistsOrThrowsException(long professionalId) {
        
        if(!professionalRepository.existsById(professionalId))
               throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Profissional com id ={" + professionalId + "} não encontrado.");
    }

}
