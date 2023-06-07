package com.abutua.agenda.usecases.read;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.entites.ProfessionalScheduleDays;
import com.abutua.agenda.entites.ProfessionalWeekDaysSlot;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.resources.exceptions.ParameterException;

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

@Service
public class ListProfessionalAvailabilityDaysUseCase {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Integer> executeUseCase(long professionalId, int month, int year) {

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado."));

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

        return availiabilyDays;

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

    // TODO Implements this in future check issue #1
    private boolean isHoliday(LocalDate currentDate) {
        return false;
    }

    // TODO Implements this in future check issue #2
    private boolean isBlockedDay(LocalDate currentDate, Professional professional) {
        return false;
    }
}
