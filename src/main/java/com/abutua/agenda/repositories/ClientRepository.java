package com.abutua.agenda.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.abutua.agenda.entites.Client;

public interface ClientRepository extends JpaRepository<Client,Long>{

    List<Client> findByNameContainingIgnoreCase(String nome, Pageable pageable);
   
}
