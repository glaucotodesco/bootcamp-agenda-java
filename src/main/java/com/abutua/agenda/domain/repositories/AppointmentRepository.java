package com.abutua.agenda.domain.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.entites.ProfessionalScheduleDays;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

        @Query("SELECT EXTRACT(DAY FROM DATE_TRUNC('day', a.date)) as day, COUNT(a.id) as total FROM Appointment a " +
                        "WHERE a.professional.id = :professionalId " +
                        "AND ( " +
                        "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.OPEN     OR " +
                        "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.PRESENT     " +
                        "     ) " +
                        "AND EXTRACT(MONTH FROM date) = :month " +
                        "AND EXTRACT(YEAR FROM date) = :year " +
                        "GROUP BY DATE_TRUNC('day', a.date)")
        List<ProfessionalScheduleDays> countAppointmentsByDayForProfessionalInMonthAndYear(Long professionalId, int month, int year);

        
        List<Appointment> findByProfessionalIdAndDate(Long professionalId, LocalDate date);

        @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
                        "WHERE a.professional = :professional " +
                        "AND a.date = :date " +
                        "AND a.startTime < :endTime " +
                        "AND a.endTime > :startTime " + 
                        "AND ( " +
                        "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.OPEN     OR " +
                        "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.PRESENT     " +
                        "     ) " 
                        )
        boolean existsOpenOrPresentAppointmentForProfessional(Professional professional, LocalDate date, LocalTime startTime,  LocalTime endTime);

        @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
                        "WHERE a.client = :client " +
                        "AND a.date = :date " +
                        "AND a.startTime < :endTime " +
                        "AND a.endTime > :startTime " + 
                        "AND ( " +
                        "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.OPEN     OR " +
                        "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.PRESENT     " +
                        "     ) " 
                        )
        boolean existsOpenOrPresentAppointmentForClient(Client client, LocalDate date, LocalTime startTime, LocalTime endTime);

}
