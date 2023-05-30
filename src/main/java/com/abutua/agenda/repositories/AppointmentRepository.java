package com.abutua.agenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abutua.agenda.entites.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
}
