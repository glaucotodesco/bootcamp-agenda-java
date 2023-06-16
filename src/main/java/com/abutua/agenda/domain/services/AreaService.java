package com.abutua.agenda.domain.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.mappers.AreaMapper;
import com.abutua.agenda.domain.mappers.ProfessionalMapper;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.DatabaseException;
import com.abutua.agenda.dto.AreaRequestDTO;
import com.abutua.agenda.dto.AreaResponseDTO;
import com.abutua.agenda.dto.AreaWithProfessionalsResponseDTO;
import com.abutua.agenda.dto.ProfessionalResponseDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    public AreaWithProfessionalsResponseDTO getById(int id) {
        var area = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area com id={" + id + "} não encontrada."));

        return AreaMapper.toResponseAreaWithProfessionalDTO(area);
    }

    public List<AreaResponseDTO> getAll() {
        return AreaMapper.toListResponseAreaDTO(areaRepository.findAll());
    }

    public AreaResponseDTO save(AreaRequestDTO areaRequestDTO) {

        Area area = AreaMapper.areaFromDTO(areaRequestDTO);

        try {
            area = areaRepository.save(area);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não existe o professional informado.", HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Professional com id é requerido", HttpStatus.BAD_REQUEST);
        }

        return AreaMapper.toResponseAreaDTO(area);
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

    public void update(int id, AreaRequestDTO areaRequestDTO) {
        try {
            var area = areaRepository.getReferenceById(id);

            var professionals = professionalRepository.findAllById(areaRequestDTO.getProfessionalsId());

            if (professionals.size() != areaRequestDTO.professionals().size()) {
                throw new DatabaseException("Profissional não cadastrado", HttpStatus.CONFLICT);
            } else {
                AreaMapper.updateArea(area, areaRequestDTO);
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

    public List<ProfessionalResponseDTO> getProfessionalsByArea(int areaId) {
        var professionalsByArea = areaRepository.findProfessionalsByAreaId(areaId);

        return professionalsByArea.stream()
                .map(p -> ProfessionalMapper.toResponseProfessionalDTO(p))
                .collect(Collectors.toList());
    }

}
