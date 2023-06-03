package com.abutua.agenda.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.entites.Professional;
import com.abutua.agenda.entites.ProfessionalWeekDaysSlot;

public interface ProfessionalRepository extends JpaRepository<Professional,Long>{
    

    @Query("SELECT w.dayOfWeek as weekDay, SUM(w.slots) as totalSlots "+
           "FROM WorkScheduleItem w " +
           "WHERE w.professional.id = :professionalId " + 
           "GROUP BY w.dayOfWeek")
    List<ProfessionalWeekDaysSlot> findDistinctDaysOfWeekAndTotalSlotsByProfessionalId(Long professionalId);

    

}
