package com.abutua.agenda.usecases.read;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.dao.TimeSlot;
import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.WorkScheduleItem;
import com.abutua.agenda.repositories.AppointmentRepository;
import com.abutua.agenda.repositories.WorkScheduleItemRepository;

@Service
public class ListProfessionalAvailabilityTimesUseCase {

    @Autowired
    private WorkScheduleItemRepository workScheduleItemRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * 
     * @param date
     * @param professionalId
     * @return
     */
    public List<TimeSlot> executeUseCase(LocalDate date, Long professionalId) {

        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        //Step 1
        List<WorkScheduleItem> workScheduleInWeekDay = workScheduleItemRepository.findWorkSchedulesByProfessionalAndDayOfWeekOrderByStartTime(professionalId, date.getDayOfWeek());

        //Step 2
        List<Appointment> appointmentsInDate = appointmentRepository.findByProfessionalIdAndDate(professionalId, date);

        //Step 3 create workschedule
        for (WorkScheduleItem workScheduleItem : workScheduleInWeekDay) {
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
