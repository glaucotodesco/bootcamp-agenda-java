package com.abutua.agenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abutua.agenda.entites.AppointmentType;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType,Integer> {
}
