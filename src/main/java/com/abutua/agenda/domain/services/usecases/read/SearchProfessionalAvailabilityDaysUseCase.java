package com.abutua.agenda.domain.services.usecases.read;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.entites.ProfessionalScheduleDays;
import com.abutua.agenda.domain.entites.ProfessionalWeekDaysSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.web.resources.exceptions.ParameterException;

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
public class SearchProfessionalAvailabilityDaysUseCase {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Integer> executeUseCase(long professionalId, int month, int year) {

        checkIfMonthIsValidOrThrowsException(month);
        checkIfYearIsValidOrThrowsException(year);
        checkIfMonthAndYearIsValidOrThrowsException(month, year);

        Professional professional = getProfessionalIfExistsOrThrowsException(professionalId);

        // Em quais dias da semana o profissional trabalha e em quais horários?
        List<ProfessionalWeekDaysSlot> professionalWeekDaysSlots = getProfessionalWorkWeedDays(professional);

        // Quais atendimentos estao agendados para o profissional no mes/ano
        List<ProfessionalScheduleDays> schedule = countAppointmentsByDayForProfessionalInMonth(month, year,
                professional);

        // Quais dias na agenda do profissional estão disponíveis
        return createAvailableDaysList(month, year, professionalWeekDaysSlots, schedule);
    }



    /******************************** Private Methods *********************************/
    private List<ProfessionalScheduleDays> countAppointmentsByDayForProfessionalInMonth(int month, int year,
            Professional professional) {

        List<ProfessionalScheduleDays> schedule = appointmentRepository
                .countAppointmentsByDayForProfessionalInMonthAndYear(professional.getId(), month, year);
        return schedule;
    }

    private List<ProfessionalWeekDaysSlot> getProfessionalWorkWeedDays(Professional professional) {
        List<ProfessionalWeekDaysSlot> professionalWeekDaysSlots = professionalRepository
                .findDistinctDaysOfWeekAndTotalSlotsByProfessionalId(professional.getId());
        return professionalWeekDaysSlots;
    }

    private Professional getProfessionalIfExistsOrThrowsException(long professionalId) {
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Profissional com id ={" + professionalId + "} não encontrado."));
        return professional;
    }

    private void checkIfMonthAndYearIsValidOrThrowsException(int month, int year) {
        if (month < LocalDate.now().getMonthValue() && year == LocalDate.now().getYear()) {
            throw new ParameterException(
                    "Valor para o mês inválido. Mês e ano deve ser maior ou igual a data corrente.");
        }
    }

    private void checkIfYearIsValidOrThrowsException(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new ParameterException("Valor para o ano inválido. O ano deve ser maior ou igual ao ano corrente.");
        }
    }

    private void checkIfMonthIsValidOrThrowsException(int month) {
        if (month < 1 || month > 12) {
            throw new ParameterException("Valor para o mês inválido.");
        }
    }

    private List<Integer> createAvailableDaysList(
            int month,
            int year,
            List<ProfessionalWeekDaysSlot> slotsWeekDays,
            List<ProfessionalScheduleDays> schedule) {

        var avaliableDaysList = new ArrayList<Integer>();

        LocalDate startDate = calcStartDate(month, year);
        LocalDate endDate = calcEndDate(startDate);
        LocalDate currentDate = startDate;

        // Varre todos os dias do mes
        while (!currentDate.isAfter(endDate)) {

            // Recupera a disponibilidade do dia
            Optional<ProfessionalWeekDaysSlot> professionalSlotsInWeekDay = getSlotsInWeekDay(slotsWeekDays, currentDate.getDayOfWeek());

            // O profissional trabalha nesse dia da semana?
            if (professionalSlotsInWeekDay.isPresent()) {
                Optional<ProfessionalScheduleDays> appointmentsInDay = getProfessionalAppointmentsInDay(schedule, currentDate.getDayOfMonth());

                if (appointmentsInDay.isPresent()) {
                    if (isAvaliableSlots(professionalSlotsInWeekDay, appointmentsInDay)) {
                        avaliableDaysList.add(currentDate.getDayOfMonth());
                    }
                } else {
                    avaliableDaysList.add(currentDate.getDayOfMonth());
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return avaliableDaysList;
    }


    private boolean isAvaliableSlots(Optional<ProfessionalWeekDaysSlot> slotsInWeekDay,
            Optional<ProfessionalScheduleDays> day) {
        return day.get().getTotal() < slotsInWeekDay.get().getTotalSlots();
    }

    private Optional<ProfessionalScheduleDays> getProfessionalAppointmentsInDay(List<ProfessionalScheduleDays> schedule, int dayOfMonth) {
        return schedule.stream().filter(s -> s.getDay() == dayOfMonth).findFirst();
    }

    private Optional<ProfessionalWeekDaysSlot> getSlotsInWeekDay(List<ProfessionalWeekDaysSlot> slotsWeekDays,
            DayOfWeek dayOfWeek) {
        Optional<ProfessionalWeekDaysSlot> slotsInDay = slotsWeekDays.stream()
                .filter(s -> s.getWeekDay().equals(dayOfWeek)).findFirst();
        return slotsInDay;
    }

    private LocalDate calcEndDate(LocalDate startDate) {
        return startDate.withDayOfMonth(startDate.lengthOfMonth());
    }

    private LocalDate calcStartDate(int month, int year) {
        var now = LocalDate.now();
        int startDay;

        if (year == now.getYear() && month == now.getMonth().getValue()) {
            startDay = LocalDate.now().getDayOfMonth();
        } else {
            startDay = 1;
        }
        return LocalDate.of(year, month, startDay);
    }

}
