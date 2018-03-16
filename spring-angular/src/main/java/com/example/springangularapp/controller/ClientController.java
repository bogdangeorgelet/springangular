package com.example.springangularapp.controller;

import com.example.springangularapp.Util.CustomErrorType;
import com.example.springangularapp.dto.ClientDto;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@RestController
public class ClientController {


    @Autowired
    ClientRepository clientRepository;

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    // -------------------Retrieve All Clients---------------------------------------------

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ResponseEntity<List<ClientDto>> listAllClients() {
        List<ClientEntity> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ClientEntity.toDtos(clients));
    }


    // -------------------Retrieve Single ClientEntity------------------------------------------

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable("id") int id) {
        logger.info("Fetching ClientEntity with id {}", id);
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            logger.error("ClientEntity with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(client.get().toDto());
    }

    // -------------------Create a ClientEntity-------------------------------------------

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody ClientDto client, UriComponentsBuilder ucBuilder) {
        logger.info("Creating ClientEntity : {}", client);

        if (clientRepository.findByCnp(client.getCnp()) != null) {
            logger.error("Unable to create. A User with name {} already exist", client.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.update(client);
        clientRepository.save(clientEntity);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    //---------------------------get all clients from a companyEntity------------------------------

    @RequestMapping(value = "/clients/company/{company_id}", method = RequestMethod.GET)
    public ResponseEntity<List<ClientDto>> listAllClientsByCompanyId(@PathVariable int company_id) {
        List<ClientEntity> clients = clientRepository.findClientsByCompanyEntityId(company_id);
        if (clients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(ClientEntity.toDtos(clients));
    }

}
