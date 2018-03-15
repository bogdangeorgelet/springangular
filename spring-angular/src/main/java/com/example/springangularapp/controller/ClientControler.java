package com.example.springangularapp.springangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@RestController
public class ClientControler {
    @Autowired
    ClientRepository clientRepository;

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    // -------------------Retrieve All Clients---------------------------------------------

    @RequestMapping(value = "/client/", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listAllClients() {
        List<Client> clients = clientRepository.findAllClients();
        if (clients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

    // -------------------Retrieve Single Client------------------------------------------

    @RequestMapping(value = "/cleint/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable("id") long id) {
        logger.info("Fetching Client with id {}", id);
        Client client = clientRepository.findById(id);
        if (client == null) {
            logger.error("Client with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Client with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    // -------------------Create a Client-------------------------------------------

    @RequestMapping(value = "/client/", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody Client client, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Client : {}", client);

        if (clientRepository.findByEmail(client.getEmail())) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        clientRepository.saveClient(client);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/client/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

}
