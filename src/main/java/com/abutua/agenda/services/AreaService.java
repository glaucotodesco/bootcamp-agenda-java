package com.abutua.agenda.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.abutua.agenda.dao.AreaWithProfessionalDAO;
import com.abutua.agenda.dao.AreaDAO;
import com.abutua.agenda.dao.AreaSaveDAO;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

 
    public AreaWithProfessionalDAO getById(int id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area not found"));

        return area.toDAOWithProfessionals();
    }

    public List<AreaDAO> getAll() {
        return areaRepository.findAll()
                .stream()
                .map(a -> a.toDAO())
                .collect(Collectors.toList());
    }

    public AreaWithProfessionalDAO save(AreaSaveDAO areaSaveDAO) {
        Area area = areaSaveDAO.toEntity();
        area = areaRepository.save(area);
        return area.toDAOWithProfessionals();
    }

    public void deleteById(int id) {
        try {
            if (areaRepository.existsById(id))
                areaRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Area not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, area can't delete");
        }
    }

    public void update(int id, AreaSaveDAO areaSaveDAO) {
        try {
            Area area = areaRepository.getReferenceById(id);
            area.setName(areaSaveDAO.getName());
            areaRepository.save(area);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Area not found");
        }
    }

}
