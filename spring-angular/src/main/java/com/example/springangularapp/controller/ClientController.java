package com.example.springangularapp.controller;

import com.example.springangularapp.dto.ClientDto;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@RestController
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    // -------------------Retrieve All Clients---------------------------------------------

    @GetMapping(value = "/clients")
    Page<ClientDto> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(ClientEntity::toDto);
    }

//    @RequestMapping(value = "/clients", method = RequestMethod.GET)
//    public ResponseEntity<List<ClientDto>> listAllClients() {
//        Iterable<ClientEntity> clients = clientService.findAllClients();
//        if (clients.) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(ClientEntity.toDtos(clients));
//    }


    // -------------------Retrieve Single ClientEntity------------------------------------------

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable("id") int id) {
        logger.info("Fetching ClientEntity with id {}", id);
        Optional<ClientEntity> client = clientService.findById(id);
        if (!client.isPresent()) {
            logger.error("ClientEntity with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(client.get().toDto());
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody ClientDto client, UriComponentsBuilder ucBuilder) {
        logger.info("Creating ClientEntity : {}", client);

        if (clientService.findByCnp(client.getCnp()) != null) {
            logger.error("Unable to create. A Client with cnp {} already exist", client.getCnp());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.update(client);
        clientService.saveClient(clientEntity);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
