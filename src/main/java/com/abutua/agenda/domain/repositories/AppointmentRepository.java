package com.abutua.agenda.domain.repositories;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.TimeSlotOffSet;


/*

WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS (
    SELECT
        start_time,
        DATEADD(MINUTE, slot_size, start_time) AS _start_time_plus_inc,
        slot_size,
        end_time,
        day_of_week
    FROM
        TBL_WORK_SCHEDULE_ITEM
    WHERE
        professional_id = 3

    UNION ALL

    SELECT
        DATEADD(MINUTE, _slot_size, _start_time),
        DATEADD(MINUTE, _slot_size, _start_time_plus_inc),
        _slot_size,
        _end_time,
        _day_of_week
    FROM
        WorkSchedule
    WHERE
        _start_time < DATEADD(MINUTE, -_slot_size, _end_time)
),
SequencialCTE AS (
    SELECT ROW_NUMBER() OVER () - 1 AS cont FROM INFORMATION_SCHEMA.COLUMNS
)
SELECT
    DAY(work_day_available)
FROM
    WorkSchedule
JOIN (
    SELECT
        DATEADD('DAY', cont, '2023-08-01') AS work_day_available,
    FROM (
        SELECT cont From SequencialCTE        
    ) 
    WHERE
        cont <= DATEDIFF('DAY', '2023-08-01', '2023-08-31')
) ON DAY_OF_WEEK(work_day_available) = _day_of_week + 2

LEFT JOIN TBL_APPOINTMENT a ON a.professional_id = 3
                            AND DAY_OF_WEEK(a.date) = _day_of_week + 2
                            AND a.start_time < _start_time_plus_inc
                            AND a.end_time > start_time
                            AND a.date = work_day_available
                            AND (a.status = 'OPEN' OR a.status = 'PRESENT')

WHERE ID IS NULL
GROUP BY work_day_available
ORDER BY work_day_available;




WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS (
    SELECT
        start_time,
        DATEADD(MINUTE, slot_size, start_time) AS _start_time_plus_inc,
        slot_size,
        end_time,
        day_of_week
    FROM
        TBL_WORK_SCHEDULE_ITEM
    WHERE
        professional_id = 3 AND
        day_of_week = 1

    UNION ALL

    SELECT
        DATEADD(MINUTE, _slot_size, _start_time),
        DATEADD(MINUTE, _slot_size, _start_time_plus_inc),
        _slot_size,
        _end_time,
        _day_of_week
    FROM
        WorkSchedule
    WHERE
        _start_time < DATEADD(MINUTE, -_slot_size, _end_time)
)
Select DISTINCT _start_time, _start_time_plus_inc, CASE 
                                                        WHEN 
                                                            date IS NULL THEN true 
                                                        ELSE false
                                                    END
FROM WorkSchedule 

LEFT JOIN TBL_APPOINTMENT a ON
           a.professional_id = 3 AND
           a.date = '2023-08-07'  AND
           DAY_OF_WEEK(a.date) = _day_of_week + 1 AND
           a.start_time < _start_time_plus_inc  AND 
           a.end_time > start_time  AND 
          (a.status = 'OPEN' OR a.status = 'PRESENT' )

ORDER BY _START_TIME 
    









*/

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = " WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS ( "
            +
            "    SELECT " +
            "        start_time, " +
            "        DATEADD(MINUTE, slot_size, start_time) AS _start_time_plus_inc, " +
            "        slot_size, " +
            "        end_time, " +
            "        day_of_week " +
            "    FROM " +
            "        TBL_WORK_SCHEDULE_ITEM " +
            "    WHERE " +
            "        professional_id = :professionalId " +
            " " +
            "    UNION ALL " +
            " " +
            "    SELECT " +
            "        DATEADD(MINUTE, _slot_size, _start_time), " +
            "        DATEADD(MINUTE, _slot_size, _start_time_plus_inc), " +
            "        _slot_size, " +
            "        _end_time, " +
            "        _day_of_week " +
            "    FROM " +
            "        WorkSchedule " +
            "    WHERE " +
            "        _start_time < DATEADD(MINUTE, -_slot_size, _end_time) " +
            "), " +
            "SequencialCTE AS ( " +
            "    SELECT ROW_NUMBER() OVER () - 1 AS cont FROM INFORMATION_SCHEMA.COLUMNS " +
            ") " +
            "SELECT " +
            "    DAY(work_day_available) AS workDay " +
            "FROM " +
            "    WorkSchedule " +
            "JOIN ( " +
            "    SELECT " +
            "        DATEADD('DAY', cont,TRIM(:start) ) AS work_day_available, " +
            "    FROM ( " +
            "        SELECT cont From SequencialCTE         " +
            "    ) " +
            "    WHERE " +
            "        cont <= DATEDIFF('DAY', :start, :end) " +
            ") ON DAY_OF_WEEK(work_day_available) = _day_of_week " +
            "LEFT JOIN TBL_APPOINTMENT a ON a.professional_id = :professionalId " +
            "                            AND DAY_OF_WEEK(a.date) = _day_of_week " +
            "                            AND a.start_time < _start_time_plus_inc " +
            "                            AND a.end_time > start_time " +
            "                            AND a.date = work_day_available " +
            "                            AND (a.status = 'OPEN' OR a.status = 'PRESENT') " +
            "WHERE ID IS NULL " +
            "GROUP BY work_day_available " +
            "ORDER BY work_day_available"

            , nativeQuery = true)
    List<Integer> getAvailabilyDays(long professionalId, LocalDate start, LocalDate end);

    List<Appointment> findByProfessionalIdAndDate(Long professionalId, LocalDate date);

    @Query( value =
    "WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS ( " +
                   "    SELECT " +
                   "        start_time, " +
                   "        DATEADD(MINUTE, slot_size, start_time) AS _start_time_plus_inc, " +
                   "        slot_size, " +
                   "        end_time, " +
                   "        day_of_week " +
                   "    FROM " +
                   "        TBL_WORK_SCHEDULE_ITEM " +
                   "    WHERE " +
                   "        professional_id = :professionalId AND " +
                   "        day_of_week = :dayOfWeek " +
                   "    UNION ALL " +
                   "    SELECT " +
                   "        DATEADD(MINUTE, _slot_size, _start_time), " +
                   "        DATEADD(MINUTE, _slot_size, _start_time_plus_inc), " +
                   "        _slot_size, " +
                   "        _end_time, " +
                   "        _day_of_week " +
                   "    FROM " +
                   "        WorkSchedule " +
                   "    WHERE " +
                   "        _start_time < DATEADD(MINUTE, -_slot_size, _end_time) " +
                   ") " +
                   "SELECT DISTINCT _start_time as startTime, _start_time_plus_inc AS endTime, CASE " +
                   "                                                        WHEN " +
                   "                                                            a.date IS NULL THEN true " +
                   "                                                        ELSE false " +
                   "                                                      END AS available " +
                   "FROM WorkSchedule " +
                   "LEFT JOIN TBL_APPOINTMENT a ON " +
                   "           a.professional_id = :professionalId AND " +
                   "           a.date = :date AND " +
                   "           a.start_time < _start_time_plus_inc  AND " +
                   "           a.end_time > start_time  AND " +
                   "          (a.status = 'OPEN' OR a.status = 'PRESENT' ) " +
                   "ORDER BY _start_time",
    nativeQuery = true
    )
    List<TimeSlotOffSet> findWorkScheduleFromProfessionalIdByDate(long professionalId, LocalDate date, int dayOfWeek);



    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.professional = :professional " +
            "AND a.date = :date " +
            "AND a.startTime < :endTime " +
            "AND a.endTime > :startTime " +
            "AND ( " +
            "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.OPEN     OR " +
            "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.PRESENT     " +
            "     ) ")
    boolean existsOpenOrPresentAppointmentForProfessional(Professional professional, LocalDate date,
            LocalTime startTime, LocalTime endTime);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.client = :client " +
            "AND a.date = :date " +
            "AND a.startTime < :endTime " +
            "AND a.endTime > :startTime " +
            "AND ( " +
            "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.OPEN     OR " +
            "       a.status = com.abutua.agenda.domain.entites.AppointmentStatus.PRESENT     " +
            "     ) ")
    boolean existsOpenOrPresentAppointmentForClient(Client client, LocalDate date, LocalTime startTime,
            LocalTime endTime);

}
