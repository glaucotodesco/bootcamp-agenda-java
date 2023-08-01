package com.abutua.agenda.web.resources;

import java.net.URI;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.domain.services.ProfessionalService;
import com.abutua.agenda.dto.ProfessionalRequest;
import com.abutua.agenda.dto.ProfessionalResponse;
import com.abutua.agenda.dto.ProfessionalWithAreasResponse;
import com.abutua.agenda.dto.TimeSlotResponse;
import com.abutua.agenda.dto.WorkScheduleResponse;

@RestController
@RequestMapping("professionals")
@CrossOrigin
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;


    @GetMapping("{id}")
    public ResponseEntity<ProfessionalWithAreasResponse> getProfessional(@PathVariable long id) {
        ProfessionalWithAreasResponse professionaldto = professionalService.getById(id);
        return ResponseEntity.ok(professionaldto);
    }

    
    @GetMapping("{id}/workshedules")
    public ResponseEntity<WorkScheduleResponse> getProfessinalWorkSchedule(@PathVariable long id) {
        WorkScheduleResponse workScheduledto = professionalService.getWorkSchedule(id);
        return ResponseEntity.ok(workScheduledto);
    }

    
    @GetMapping("{id}/availability-days")
    public ResponseEntity<List<Integer>> getProfessinalAvailabitiyDays(@PathVariable long id, @RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(professionalService.getAvailableDaysInMonth(id,month,year));
    }

    @GetMapping("{id}/availability-times")
    public ResponseEntity<List<TimeSlotResponse>> getProfessinalAvailabitiyTimes(@PathVariable Long id, @RequestParam String date) {
        List <TimeSlotResponse> slots = professionalService.getAvailableTimeSlots(LocalDate.parse(date),id);
        return ResponseEntity.ok(slots);
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalResponse>> getProfessionals() {
        return ResponseEntity.ok(professionalService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeProfessional(@PathVariable long id) {
        professionalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProfessional(@PathVariable long id, @Validated @RequestBody ProfessionalRequest professionalSavedto) {
        professionalService.update(id, professionalSavedto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ProfessionalResponse> saveProfessional(@Validated @RequestBody ProfessionalRequest professioanalSavedto) {
        var professionaldto = professionalService.save(professioanalSavedto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professionaldto.id())
                .toUri();

        return ResponseEntity.created(location).body(professionaldto);
    }


}
