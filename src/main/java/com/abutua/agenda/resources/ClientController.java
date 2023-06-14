package com.abutua.agenda.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.dto.ClientResponseDTO;
import com.abutua.agenda.dto.ClientRequestDTO;
import com.abutua.agenda.services.ClientService;

@RestController
@CrossOrigin
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> getClients(
            @RequestParam(name = "name_like", defaultValue = "") String nameLike,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit

    ) {
        return ResponseEntity.ok().body(clientService.findByNameContaining(nameLike, limit, page));
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable long id) {
        ClientResponseDTO areadto = clientService.getById(id);
        return ResponseEntity.ok(areadto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeClient(@PathVariable long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateClient(@PathVariable int id,
            @Validated @RequestBody ClientRequestDTO clientSaveDTO) {
        clientService.update(id, clientSaveDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> saveClient(@Validated @RequestBody ClientRequestDTO clientSaveDTO) {
        var clientDTO = clientService.save(clientSaveDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(clientDTO);
    }

}
