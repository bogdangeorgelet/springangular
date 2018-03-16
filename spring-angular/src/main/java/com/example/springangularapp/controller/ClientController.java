package com.example.springangularapp.controller;

import com.example.springangularapp.Util.CustomErrorType;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.entity.Client;
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

    ClientController() {

    }
    // -------------------Retrieve All Clients---------------------------------------------

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listAllClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }


    // -------------------Retrieve Single Client------------------------------------------

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable("id") int id) {
        logger.info("Fetching Client with id {}", id);
        Optional<Client> client = clientRepository.findById(id);
        if (client == null) {
            logger.error("Client with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Client with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
    }

    // -------------------Create a Client-------------------------------------------

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody Client client, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Client : {}", client);

        if (clientRepository.findByCnp(client.getCnp()) != null) {
            logger.error("Unable to create. A User with name {} already exist", client.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    client.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        clientRepository.save(client);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/client/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<String>("S-a introdus cu succes",HttpStatus.CREATED);
    }


    //---------------------------get all clients from a company------------------------------

    @RequestMapping(value = "/clients/company/{company_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listAllClientsByCompanyId(@PathVariable int company_id) {
        List<Client> clients = clientRepository.findClientsByCompanyId(company_id);
        if (clients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

}
