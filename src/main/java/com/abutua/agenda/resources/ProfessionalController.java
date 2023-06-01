package com.abutua.agenda.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.dao.AreaWithProfessionalDAO;
import com.abutua.agenda.dao.ProfessionalDAO;
import com.abutua.agenda.dao.ProfessionalSaveDAO;
import com.abutua.agenda.dao.ProfessionalWithAreaDAO;
import com.abutua.agenda.dao.AreaDAO;
import com.abutua.agenda.dao.AreaSaveDAO;
import com.abutua.agenda.services.AreaService;
import com.abutua.agenda.services.ProfessionalService;

@RestController
@RequestMapping("professionals")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;


    @GetMapping("{id}")
    public ResponseEntity<ProfessionalWithAreaDAO> getArea(@PathVariable int id) {
        ProfessionalWithAreaDAO professionalDAO = professionalService.getById(id);
        return ResponseEntity.ok(professionalDAO);
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalDAO>> getAreas() {
        return ResponseEntity.ok(professionalService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeProfessional(@PathVariable long id) {
        professionalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProfessional(@PathVariable long id, @Validated @RequestBody ProfessionalSaveDAO professionalSaveDAO) {
        professionalService.update(id, professionalSaveDAO);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ProfessionalDAO> save(@Validated @RequestBody ProfessionalSaveDAO professioanalSaveDAO) {
        ProfessionalDAO professionalDAO = professionalService.save(professioanalSaveDAO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professionalDAO.getId())
                .toUri();

        return ResponseEntity.created(location).body(professionalDAO);
    }
}
