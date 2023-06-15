package com.abutua.agenda.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Professional;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    @Query("SELECT p FROM Professional p JOIN p.areas a WHERE a.id = :areaId")
    List<Professional> findProfessionalsByAreaId(Integer areaId);


    @Query("SELECT a FROM Area a JOIN a.professionals p WHERE a.id = :areaId AND p.id = :professionalId")
    Optional<Area> findAreaByProfessionalId(Long professionalId, Integer areaId);
}
