package com.abutua.agenda.services;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.dao.ProfessionalAvailabilityDaysDAO;
import com.abutua.agenda.dao.ProfessionalDAO;
import com.abutua.agenda.dao.ProfessionalSaveDAO;
import com.abutua.agenda.dao.ProfessionalWithAreaDAO;
import com.abutua.agenda.dao.WorkScheduleDAO;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.entites.ProfessionalScheduleDays;
import com.abutua.agenda.entites.ProfessionalWeekDaysSlot;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.resources.exceptions.ParameterException;
import com.abutua.agenda.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public ProfessionalWithAreaDAO getById(long id) {
        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        return professional.toDAOWithAreas();
    }

    public List<ProfessionalDAO> getAll() {
        return professionalRepository.findAll()
                .stream()
                .map(p -> p.toDAO())
                .collect(Collectors.toList());
    }

    public ProfessionalDAO save(ProfessionalSaveDAO professionalSaveDAO) {
        Professional professional;
        try {
            professional = professionalRepository.save(professionalSaveDAO.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation. Tips: Check for valid areas id or unique email ",
                    HttpStatus.CONFLICT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id professional is required", HttpStatus.BAD_REQUEST);
        }
        return professional.toDAO();
    }

    public void deleteById(long id) {
        try {
            if (professionalRepository.existsById(id))
                professionalRepository.deleteById(id);
            else {
                throw new EntityNotFoundException("Professional not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, professional can't delete", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(long id, ProfessionalSaveDAO professionalSaveDAO) {
        try {
            Professional professional = professionalRepository.getReferenceById(id);

            List<Area> areas = areaRepository.findAllById(professionalSaveDAO.getAreasId());

            if (areas.size() != professionalSaveDAO.getAreas().size()) {
                throw new DatabaseException("Constrain violation, id area doesn't exist", HttpStatus.CONFLICT);
            } else {
                professional.setName(professionalSaveDAO.getName());
                professional.setEmail(professionalSaveDAO.getEmail());
                professional.setPhone(professionalSaveDAO.getPhone());
                professional.setName(professionalSaveDAO.getName());
                professional.setActive(professionalSaveDAO.isActive());
                professional.getAreas().clear();
                professional.getAreas().addAll(professionalSaveDAO.toEntity().getAreas());
                professionalRepository.save(professional);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Professional not found");
        } catch (InvalidDataAccessApiUsageException e) {
            throw new DatabaseException("Id area is required", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation. Tips: Check for valid areas id or unique email ",
                    HttpStatus.CONFLICT);
        }
    }

    public WorkScheduleDAO getWorkSchedule(long id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        return professional.toWorkScheduleDAO();
    }

    public ProfessionalAvailabilityDaysDAO getAvailabilityDays(long id, int month, int year) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

        ProfessionalAvailabilityDaysDAO dao = new ProfessionalAvailabilityDaysDAO();
        dao.setId(professional.getId());
        dao.setName(professional.getName());

        try {
            Month.of(month);
            dao.setMonth(month);
        } catch (DateTimeException e) {
            throw new ParameterException("Invalid parameter month value");
        }

        if (year < LocalDate.now().getYear()) {
            throw new ParameterException("Invalid parameter year value. Year must be greater than or equal to the current year");
        }
        else{
            dao.setYear(year);
        }
        //Quantos atendimentos por dia da semana estão disponíveis
        List<ProfessionalWeekDaysSlot> professionalWeekDaysSlots = professionalRepository.findDistinctDaysOfWeekAndTotalSlotsByProfessionalId(professional.getId());

        //Quais atendimentos estao agendados
        List<ProfessionalScheduleDays> schedule = appointmentRepository.countAppointmentsByDayForProfessionalInMonthAndYear(professional.getId(), month, year);

        //Dias disponíveis
        List<Integer> availiabilyDays = getWorksDays(month, year,professionalWeekDaysSlots, schedule);


        dao.setAvailabilityDays(availiabilyDays);

        

        return dao;
    }

    private List<Integer> getWorksDays(int month, int year, List<ProfessionalWeekDaysSlot> slotsWeekDays, List<ProfessionalScheduleDays> schedule) {
        List<Integer> daysOfMonth = new ArrayList<Integer>();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            
            //Exclui Sabado e Domingo
            if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY && currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {

                //Recuperar os slots desse dia da semana.
                final int dayOfWeek  = currentDate.getDayOfWeek().getValue();
                
                //Recuperar o dia do mes
                final int dayOfMonth = currentDate.getDayOfMonth();

                Optional<ProfessionalWeekDaysSlot> slot = slotsWeekDays.stream().filter( s -> s.getWeekDay() == dayOfWeek ).findFirst();
                if(slot.isPresent()){
                    int total = slot.get().getTotalSlots();

                    Optional<ProfessionalScheduleDays> day = schedule.stream().filter( s -> s.getDay() == dayOfMonth).findFirst();
 
                    if(day.isPresent()){
                        if(day.get().getTotal() < total) {
                            daysOfMonth.add(currentDate.getDayOfMonth());
                        }
                    }
                    else{
                        daysOfMonth.add(currentDate.getDayOfMonth());
                    }
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        
        return daysOfMonth;
    }
}
