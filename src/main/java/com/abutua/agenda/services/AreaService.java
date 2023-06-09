package com.abutua.agenda.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.dto.AreaDTO;
import com.abutua.agenda.dto.AreaSaveDTO;
import com.abutua.agenda.dto.AreaWithProfessionalDTO;
import com.abutua.agenda.dto.ProfessionalDTO;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    public AreaWithProfessionalDTO getById(int id) {

        var area = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area com id={" + id + "} não encontrada."));

        return area.toDTOWithProfessionals();
    }

    public List<AreaDTO> getAll() {
        return areaRepository.findAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }

    public AreaDTO save(AreaSaveDTO areaSavedto) {
        Area area;

        try {
            area = areaRepository.save(areaSavedto.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não existe o professional informado.", HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Professional com id é requerido", HttpStatus.BAD_REQUEST);
        }
        return area.toDTO();
    }

    public void deleteById(int id) {
        try {
            if (areaRepository.existsById(id))
                areaRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Area com id={" + id + "} não encontrada.");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Conflito ao remover a Area", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(int id, AreaSaveDTO areaSavedto) {
        try {
            var area = areaRepository.getReferenceById(id);

            var professionals = professionalRepository.findAllById(areaSavedto.getProfessionalsId());

            if (professionals.size() != areaSavedto.professionalsdto().size()) {
                throw new DatabaseException("Profissional não cadastrado", HttpStatus.CONFLICT);
            } else {
                area.setName(areaSavedto.name());
                area.getProfessionals().clear();
                area.getProfessionals().addAll(areaSavedto.toEntity().getProfessionals());
                areaRepository.save(area);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Conflito, violação do banco de dados.", HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Area com id={" + id + "} não encontrada.");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Informar o professional", HttpStatus.BAD_REQUEST);
        }
    }

    public List<ProfessionalDTO> getProfessionalsByArea(int id) {
        return areaRepository.findProfessionalsByAreaId(id).stream()
                .map(p -> p.toDTO())
                .collect(Collectors.toList());
    }

}
