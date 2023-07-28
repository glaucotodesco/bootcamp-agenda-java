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


/*

- Índice para a coluna professional_id na tabela TBL_APPOINTMENT
CREATE INDEX idx_appointment_professional_id ON TBL_APPOINTMENT (professional_id);

-- Índice para a coluna date na tabela TBL_APPOINTMENT
CREATE INDEX idx_appointment_date ON TBL_APPOINTMENT (date);

-- Índice para a coluna status na tabela TBL_APPOINTMENT
CREATE INDEX idx_appointment_status ON TBL_APPOINTMENT (status);

-- Índice para a coluna professional_id na tabela TBL_WORK_SCHEDULE_ITEM
CREATE INDEX idx_work_schedule_item_professional_id ON TBL_WORK_SCHEDULE_ITEM (professional_id);

-- Índice para a coluna day_of_week na tabela TBL_WORK_SCHEDULE_ITEM
CREATE INDEX idx_work_schedule_item_day_of_week ON TBL_WORK_SCHEDULE_ITEM (day_of_week);

WITH DailyAvailability AS (
    SELECT 
        DAY_OF_MONTH(a.date) as day_of_month,
        COUNT(a.id) as total_appointments
    FROM 
        TBL_APPOINTMENT AS a
    WHERE
        a.professional_id = 4
        AND a.date BETWEEN '2024-04-01' AND '2024-04-30'
        AND (a.status = 'OPEN' OR a.status = 'PRESENT')
    GROUP BY
        DAY_OF_MONTH(a.date)
),
WeeklyAvailability AS (
    SELECT 
        w.day_of_week,
        SUM(w.slots) as total_slots
    FROM 
        TBL_WORK_SCHEDULE_ITEM AS w
    WHERE 
        w.professional_id = 4
    GROUP BY 
        w.day_of_week
)
SELECT
    d.day_of_month,  w.total_slots, w.total_slots - COALESCE(da.total_appointments,0) as TOTAL_VAGAS,  COALESCE(da.total_appointments,0)
FROM
    (
            SELECT DATEADD('DAY', n, '2024-04-01')  AS date, DAY(DATEADD('DAY', n, '2024-04-01')) AS DAY_OF_MONTH FROM (
               SELECT ROW_NUMBER() OVER () - 1 AS n
                                FROM INFORMATION_SCHEMA.TABLES
               ) numbers
             WHERE n <= DATEDIFF('DAY', '2024-04-01', '2024-04-30')
    ) d
JOIN
    WeeklyAvailability w ON DAY_OF_WEEK(d.date) = w.day_of_week
LEFT JOIN
    DailyAvailability da ON d.day_of_month = da.day_of_month
WHERE   w.total_slots - COALESCE(da.total_appointments,0) > 0
ORDER BY
    d.day_of_month




*/


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
