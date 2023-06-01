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
import com.abutua.agenda.dao.AreaDAO;
import com.abutua.agenda.dao.AreaSaveDAO;
import com.abutua.agenda.services.AreaService;

@RestController
@RequestMapping("areas")
public class AreaController {

    @Autowired
    private AreaService areaService;


    @GetMapping("{id}")
    public ResponseEntity<AreaWithProfessionalDAO> getArea(@PathVariable int id) {
        AreaWithProfessionalDAO areaDAO = areaService.getById(id);
        return ResponseEntity.ok(areaDAO);
    }

    @GetMapping
    public ResponseEntity<List<AreaDAO>> getAreas() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeArea(@PathVariable int id) {
        areaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateArea(@PathVariable int id, @Validated @RequestBody AreaSaveDAO areaSaveDAO) {
        areaService.update(id, areaSaveDAO);
        return ResponseEntity.ok().build();
    }

    
    @PostMapping
    public ResponseEntity<AreaWithProfessionalDAO> save(@Validated @RequestBody AreaSaveDAO areaSaveDAO) {
        AreaWithProfessionalDAO areaDAO = areaService.save(areaSaveDAO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(areaDAO.getId())
                .toUri();

        return ResponseEntity.created(location).body(areaDAO);
    }
}
