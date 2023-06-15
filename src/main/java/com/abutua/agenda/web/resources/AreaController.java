package com.abutua.agenda.web.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.abutua.agenda.domain.services.AreaService;
import com.abutua.agenda.dto.AreaRequestDTO;
import com.abutua.agenda.dto.AreaResponseDTO;
import com.abutua.agenda.dto.AreaWithProfessionalsResponseDTO;
import com.abutua.agenda.dto.ProfessionalResponseDTO;


@RestController
@RequestMapping("areas")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaController {

    @Autowired
    private AreaService areaService;

    
    @GetMapping("{id}")
    public ResponseEntity<AreaWithProfessionalsResponseDTO> getArea(@PathVariable int id) {
        AreaWithProfessionalsResponseDTO areadto = areaService.getById(id);
        return ResponseEntity.ok(areadto);
    }

    @GetMapping("{id}/professionals")
    public ResponseEntity<List<ProfessionalResponseDTO>> getProfessionalsByArea(@PathVariable int id) {
        return ResponseEntity.ok(areaService.getProfessionalsByArea(id));
    }


    @GetMapping
    public ResponseEntity<List<AreaResponseDTO>> getAreas() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> removeArea(@PathVariable int id) {
        areaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> updateArea(@PathVariable int id, @Validated @RequestBody AreaRequestDTO areaSavedto) {
        areaService.update(id, areaSavedto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AreaResponseDTO> saveArea(@Validated @RequestBody AreaRequestDTO areaSavedto) {
        var areadto = areaService.save(areaSavedto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(areadto.id())
                .toUri();

        return ResponseEntity.created(location).body(areadto);
    }
}
