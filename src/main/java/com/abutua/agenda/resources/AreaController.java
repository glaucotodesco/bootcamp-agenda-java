package com.abutua.agenda.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.dto.AreaDTO;
import com.abutua.agenda.dto.AreaSaveDTO;
import com.abutua.agenda.dto.AreaWithProfessionalDTO;
import com.abutua.agenda.dto.ProfessionalDTO;
import com.abutua.agenda.services.AreaService;


@RestController
@RequestMapping("areas")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaController {

    @Autowired
    private AreaService areaService;

    
    @GetMapping("{id}")
    public ResponseEntity<AreaWithProfessionalDTO> getArea(@PathVariable int id) {
        AreaWithProfessionalDTO areadto = areaService.getById(id);
        return ResponseEntity.ok(areadto);
    }

    @GetMapping("{id}/professionals")
    public ResponseEntity<List<ProfessionalDTO>> getProfessionalsByArea(@PathVariable int id) {
        return ResponseEntity.ok(areaService.getProfessionalsByArea(id));
    }


    @GetMapping
    public ResponseEntity<List<AreaDTO>> getAreas() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeArea(@PathVariable int id) {
        areaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateArea(@PathVariable int id, @Validated @RequestBody AreaSaveDTO areaSavedto) {
        areaService.update(id, areaSavedto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<AreaDTO> saveArea(@Validated @RequestBody AreaSaveDTO areaSavedto) {
        var areadto = areaService.save(areaSavedto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(areadto.id())
                .toUri();

        return ResponseEntity.created(location).body(areadto);
    }
}
