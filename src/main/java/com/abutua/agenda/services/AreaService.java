package com.abutua.agenda.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.abutua.agenda.dao.AreaWithProfessionalDAO;
import com.abutua.agenda.dao.ProfessionalDAO;
import com.abutua.agenda.dao.AreaDAO;
import com.abutua.agenda.dao.AreaSaveDAO;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
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

    public AreaWithProfessionalDAO getById(int id) {

        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area with " + id + " not found"));

        return area.toDAOWithProfessionals();
    }

    public List<AreaDAO> getAll() {
        return areaRepository.findAll()
                .stream()
                .map(a -> a.toDAO())
                .collect(Collectors.toList());
    }

    public AreaDAO save(AreaSaveDAO areaSaveDAO) {
        Area area;

        try {
            area = areaRepository.save(areaSaveDAO.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, id professional doesn't exist", HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id professional is required", HttpStatus.BAD_REQUEST);
        }
        return area.toDAO();
    }

    public void deleteById(int id) {
        try {
            if (areaRepository.existsById(id))
                areaRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Area not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, area can't delete", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(int id, AreaSaveDAO areaSaveDAO) {
        try {
            Area area = areaRepository.getReferenceById(id);

            List<Professional> professionals = professionalRepository.findAllById(areaSaveDAO.getProfessionalsId());

            if (professionals.size() != areaSaveDAO.getProfessionals().size()) {
                throw new DatabaseException("Constrain violation, id professional doesn't exist", HttpStatus.CONFLICT);
            } else {
                area.setName(areaSaveDAO.getName());
                area.getProfessionals().clear();
                area.getProfessionals().addAll(areaSaveDAO.toEntity().getProfessionals());
                areaRepository.save(area);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, id professional doesn't exist", HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Area not found");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id professional is required", HttpStatus.BAD_REQUEST);
        }
    }

    public List<ProfessionalDAO> getProfessionalsByArea(int id) {
        return areaRepository.findProfessionalsByAreaId(id).stream()
                .map(p -> p.toDAO())
                .collect(Collectors.toList());
    }

}
