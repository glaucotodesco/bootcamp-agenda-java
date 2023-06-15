package com.abutua.agenda.domain.repositories;

import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.domain.entites.WorkScheduleItem;

public interface WorkScheduleItemRepository extends JpaRepository<WorkScheduleItem, Long> {

    @Query("SELECT w FROM WorkScheduleItem " +
            "w WHERE w.professional.id = :professionalId AND " +
            "w.dayOfWeek = :dayOfWeek " +
            "ORDER BY w.startTime")
    List<WorkScheduleItem> findWorkSchedulesByProfessionalAndDayOfWeekOrderByStartTime(Long professionalId,
            DayOfWeek dayOfWeek);

}
