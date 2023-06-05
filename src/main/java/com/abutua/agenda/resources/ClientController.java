package com.abutua.agenda.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.dao.ClientDAO;
import com.abutua.agenda.services.ClientService;

@RestController
@CrossOrigin
@RequestMapping("clients")
public class ClientController {
    
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDAO>> getClients(@RequestParam(name = "name_like", defaultValue = "") String name_like,
                                                      @RequestParam(name = "limit", defaultValue = "10") int limit
                                                     ){

        return ResponseEntity.ok().body(clientService.findByNameContaining(name_like, limit));
    }


}
