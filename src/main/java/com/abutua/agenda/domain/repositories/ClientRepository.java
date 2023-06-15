package com.abutua.agenda.domain.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entites.Client;

public interface ClientRepository extends JpaRepository<Client,Long>{

    Page<Client> findByNameContainingIgnoreCase(String nome, Pageable pageable);
   
}
