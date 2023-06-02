package com.abutua.agenda.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.entites.Appointment;
import com.abutua.agenda.entites.ProfessionalScheduleDays;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT EXTRACT(DAY FROM DATE_TRUNC('day', a.date)) as day, COUNT(a.id) as total FROM Appointment a " +
            "WHERE a.professional.id = :professionalId " +
            "AND EXTRACT(MONTH FROM date) = :month " +
            "AND EXTRACT(YEAR FROM date) = :year " +
            "GROUP BY DATE_TRUNC('day', a.date)")
    List<ProfessionalScheduleDays> countAppointmentsByDayForProfessionalInMonthAndYear(Long professionalId, int month, int year);

}
