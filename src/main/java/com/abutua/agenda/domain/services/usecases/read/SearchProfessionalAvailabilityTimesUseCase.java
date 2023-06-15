package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.WorkScheduleItem;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.WorkScheduleItemRepository;
import com.abutua.agenda.dto.TimeSlotResponseDTO;

@Service
public class SearchProfessionalAvailabilityTimesUseCase {

    @Autowired
    private WorkScheduleItemRepository workScheduleItemRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<TimeSlotResponseDTO> executeUseCase(LocalDate date, Long professionalId) {

        var availableTimeInDate = new ArrayList<TimeSlotResponseDTO>();

        var professionalWorkScheduleInDate = getWorkScheduleInDate(date, professionalId);
        var professionalAppointmentInDate = appointmentRepository.findByProfessionalIdAndDate(professionalId, date);

        //Step 3 create workschedule
        for (WorkScheduleItem workScheduleItem : professionalWorkScheduleInDate) {
            // Calcular os slots disponíveis com base nos horários de trabalho e agendamentos existentes
            List<TimeSlotResponseDTO> availableSlots = calculateAvailableSlots(workScheduleItem, professionalAppointmentInDate, date);
            // Adicionar os slots disponíveis à lista final
            availableTimeInDate.addAll(availableSlots);
        }

        return availableTimeInDate;
    }


    private List<WorkScheduleItem> getWorkScheduleInDate(LocalDate date, Long professionalId) {
        List<WorkScheduleItem> workScheduleInWeekDay = workScheduleItemRepository.findWorkSchedulesByProfessionalAndDayOfWeekOrderByStartTime(professionalId, date.getDayOfWeek());
        return workScheduleInWeekDay;
    }


    private List<TimeSlotResponseDTO> calculateAvailableSlots(WorkScheduleItem workScheduleItem, List<Appointment> appointments, LocalDate date) {
       
        List<TimeSlotResponseDTO> availableSlots = new ArrayList<>();

        LocalTime startTime = workScheduleItem.getStartTime();
        Integer totalSlots = workScheduleItem.getSlots();
        Integer numberOfSlots = workScheduleItem.getSlotSize();

        // Verificar se há agendamentos nos horários de trabalho e ajustar os slots
        // disponíveis
        for (int i = 0; i < totalSlots; i++) {

            LocalTime slotStartTime = startTime.plusMinutes(i * numberOfSlots);
            LocalTime slotEndTime = slotStartTime.plusMinutes(numberOfSlots);

            boolean isSlotUsed = isSlotUsed(appointments,slotStartTime, slotEndTime);
            boolean isSlotInPast = isTimeInvalidIfDateIsToday(slotStartTime, date);
            
            if (isSlotUsed || isSlotInPast) {
                availableSlots.add(new TimeSlotResponseDTO(slotStartTime, slotEndTime, false));
            }
            else{
                availableSlots.add(new TimeSlotResponseDTO(slotStartTime, slotEndTime, true));
            }
        }

        return availableSlots;
    }

    private boolean isSlotUsed(List<Appointment> appointments,  LocalTime slotStartTime,  LocalTime slotEndTime) {
       return appointments.stream()
                            .anyMatch(appointment -> appointment.getStartTime().isBefore(slotEndTime) && 
                                                     appointment.getEndTime().isAfter(slotStartTime));
    }

    private boolean isTimeInvalidIfDateIsToday(LocalTime slotStartTime, LocalDate date) {
       return slotStartTime.isBefore(LocalTime.now()) && date.equals(LocalDate.now());
    }
    
}
