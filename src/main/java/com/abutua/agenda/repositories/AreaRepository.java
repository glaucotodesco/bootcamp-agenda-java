package com.abutua.agenda.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.entites.Area;
import com.abutua.agenda.entites.Professional;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    @Query("SELECT p FROM Professional p JOIN p.areas a WHERE a.id = :areaId")
    List<Professional> findProfessionalsByAreaId(Integer areaId);
}
