package com.abutua.agenda.services;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import com.abutua.agenda.dao.TimeSlot;
import com.abutua.agenda.dao.WorkScheduleDAO;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.entites.ProfessionalScheduleDays;
import com.abutua.agenda.entites.ProfessionalWeekDaysSlot;
import com.abutua.agenda.entites.WorkScheduleItem;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.AreaRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.repositories.WorkScheduleItemRepository;
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

    @Autowired
    private WorkScheduleItemRepository workScheduleItemRepository;

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

    /**
     * 
     * @param professionalId
     * @param month          Valid value and now or future
     * @param year           Now or future
     * 
     * @return
     *
     * @throws ParameterException("Invalid parameter month value.");
     * @throws ParameterException("Invalid parameter year value. Year must be
     *                                     greater than or equal to the current
     *                                     year.")
     * @throws ParameterException("Invalid parameter month value. Month and Year
     *                                     must be greater than or equal to the curr
     */
    public ProfessionalAvailabilityDaysDAO getAvailableDaysInMonth(long professionalId, int month, int year) {

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professional not found"));

       
        try {
            Month.of(month);
        } catch (DateTimeException e) {
            throw new ParameterException("Invalid parameter month value.");
        }

        if (year < LocalDate.now().getYear()) {
            throw new ParameterException(
                    "Invalid parameter year value. Year must be greater than or equal to the current year.");
        } else if (month < LocalDate.now().getMonthValue() && year == LocalDate.now().getYear()) {
            throw new ParameterException(
                    "Invalid parameter month value. Month and Year must be greater than or equal to the current date.");
        }

        // Em quais dias da semana o profissional trabalha e em quais horários?
        List<ProfessionalWeekDaysSlot> professionalWeekDaysSlots = professionalRepository
                .findDistinctDaysOfWeekAndTotalSlotsByProfessionalId(professional.getId());

        // Quais atendimentos estao agendados para o profissional no mes/ano
        List<ProfessionalScheduleDays> schedule = appointmentRepository
                .countAppointmentsByDayForProfessionalInMonthAndYear(professional.getId(), month, year);

        // Quais dias na agenda do profissional estão disponíveis
        List<Integer> availiabilyDays = createAvailableDaysList(month, year, professionalWeekDaysSlots, schedule,
                professional);

        ProfessionalAvailabilityDaysDAO dao = new ProfessionalAvailabilityDaysDAO();
        dao.setId(professional.getId());
        dao.setName(professional.getName());
        dao.setMonth(month);
        dao.setYear(year);
        dao.setAvailabilityDays(availiabilyDays);

        return dao;
    }

    /**
     * 
     * @param month
     * @param year
     * @param slotsWeekDays
     * @param schedule
     * @param professional
     * 
     * @return
     */
    private List<Integer> createAvailableDaysList(int month, int year, List<ProfessionalWeekDaysSlot> slotsWeekDays,
            List<ProfessionalScheduleDays> schedule, Professional professional) {

        List<Integer> daysOfMonth = new ArrayList<Integer>();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        LocalDate currentDate = startDate;

        // Varre todos os dias do mes
        while (!currentDate.isAfter(endDate)) {

            // Exclui feriados e dias bloqueados
            if (!isHoliday(currentDate) && !isBlockedDay(currentDate, professional)) {

                // Recuperar o dia da semmana
                DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

                // Recuperar o dia do mes
                final int dayOfMonth = currentDate.getDayOfMonth();

                Optional<ProfessionalWeekDaysSlot> slot = slotsWeekDays.stream()
                        .filter(s -> s.getWeekDay().equals(dayOfWeek)).findFirst();

                // O profissional trabalha nesse dia da semana?
                if (slot.isPresent()) {

                    // Recupera a quantidade de atendimentos possiveis do professional no dia da
                    // semana
                    int totalSlots = slot.get().getTotalSlots();

                    // Recupera quantos atendimentos já estão agendados para esse dia
                    Optional<ProfessionalScheduleDays> day = schedule.stream().filter(s -> s.getDay() == dayOfMonth)
                            .findFirst();

                    if (day.isPresent()) {
                        // Verifica se existem vagas (slots) disponiveis para esse dia.
                        if (day.get().getTotal() < totalSlots) {
                            daysOfMonth.add(currentDate.getDayOfMonth());
                        }
                    } else {
                        daysOfMonth.add(currentDate.getDayOfMonth());
                    }
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return daysOfMonth;
    }

    // TODO Implements this in future
    private boolean isHoliday(LocalDate currentDate) {
        return false;
    }

    // TODO Implements this in future
    private boolean isBlockedDay(LocalDate currentDate, Professional professional) {
        return false;
    }

    /**
     * 
     * @param date
     * @param professionalId
     * @return
     */
    public List<TimeSlot> getAvailableTimeSlots(LocalDate date, Long professionalId) {
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        // Obter os horários possiveis de trabalho do profissional para o dia da semana
        // especificado
        List<WorkScheduleItem> workScheduleItemsInWeekDay = workScheduleItemRepository
                .findByProfessionalIdAndDayOfWeek(professionalId, date.getDayOfWeek());

        // Obter os agendamentos do profissional para o dia especificado
        List<Appointment> appointmentsInDate = appointmentRepository.findByProfessionalIdAndDate(professionalId, date);

        // Processar os horários de trabalho e encontrar os slots disponíveis
        for (WorkScheduleItem workScheduleItem : workScheduleItemsInWeekDay) {

            // Calcular os slots disponíveis com base nos horários de trabalho e
            // agendamentos existentes
            List<TimeSlot> availableSlots = calculateAvailableSlots(workScheduleItem, appointmentsInDate);

            // Adicionar os slots disponíveis à lista final
            availableTimeSlots.addAll(availableSlots);
        }

        return availableTimeSlots;
    }

    /**
     * 
     * @param workScheduleItem
     * @param appointments
     * @return
     */
    private List<TimeSlot> calculateAvailableSlots(WorkScheduleItem workScheduleItem, List<Appointment> appointments) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        LocalTime startTime = workScheduleItem.getStartTime();
        Integer slots = workScheduleItem.getSlots();
        Integer slotSize = workScheduleItem.getSlotSize();

        // Verificar se há agendamentos nos horários de trabalho e ajustar os slots
        // disponíveis
        for (int i = 0; i < slots; i++) {

            LocalTime slotStartTime = startTime.plusMinutes(i * slotSize);
            LocalTime slotEndTime = slotStartTime.plusMinutes(slotSize);

            boolean isSlotAvailable = true;

            for (Appointment appointment : appointments) {

                LocalTime appointmentStartTime = appointment.getStartTime();
                LocalTime appointmentEndTime = appointment.getEndTime();

                if (appointmentStartTime.isBefore(slotEndTime) && appointmentEndTime.isAfter(slotStartTime)) {
                    // O slot está ocupado por um agendamento existente
                    isSlotAvailable = false;
                    break;
                }
            }

            if (isSlotAvailable) {
                // O slot está disponível
                availableSlots.add(new TimeSlot(slotStartTime, slotEndTime, true));
            }
            else{
                availableSlots.add(new TimeSlot(slotStartTime, slotEndTime, false));
            }
        }

        return availableSlots;
    }
}
