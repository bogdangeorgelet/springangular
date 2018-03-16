package com.example.springangularapp.controller;

import com.example.springangularapp.Util.CustomErrorType;
import com.example.springangularapp.entity.Client;
import com.example.springangularapp.entity.Company;
import com.example.springangularapp.entity.Review;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.repository.CompanyRepository;
import com.example.springangularapp.repository.ReviewRepository;
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
public class ReviewController {
    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ClientRepository clientRepository;


    //---------------------------get all reviews from a company by Id------------------------------

    @RequestMapping(value = "/reviews/company/{company_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> listAllReviewsByCompanyId(@PathVariable int company_id) {
        List<Review> reviews = (List<Review>) reviewRepository.findReviewsByCompanyId(company_id);
        if (reviews.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
    }

    //---------------------------get all reviews from a client by Id------------------------------

    @RequestMapping(value = "/reviews/client/{client_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> listAllReviewsByClientId(@PathVariable int client_id) {
        List<Review> reviews = (List<Review>) reviewRepository.findReviewsByClientId(client_id);
        if (reviews.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
    }


    // -------------------Retrieve All Clients---------------------------------------------

    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> listAllReviews() {
        List<Review> clients = reviewRepository.findAll();
        if (clients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Review>>(clients, HttpStatus.OK);
    }


    // -------------------Insert a review-------------------------------------------

    @RequestMapping(value = "/review/company/{company_id}/client/{client_id}", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody Review review, @PathVariable int company_id, @PathVariable int client_id, UriComponentsBuilder ucBuilder) {
        logger.info("Creating review : {}", review);
        System.out.println("company id:" + company_id);
        System.out.println("clieny id:" + client_id);
        Optional<Company> company = companyRepository.findById(company_id);
        Optional<Client> client = clientRepository.findById(client_id);
        if (company.isPresent() && client.isPresent()) {
            review.setClient(client.get());
            review.setCompany(company.get());
            reviewRepository.save(review);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/client/{id}").buildAndExpand(review.getId()).toUri());
            return new ResponseEntity<String>("S-a introdus cu succes reviewul", HttpStatus.CREATED);
        } else
            return new ResponseEntity(new CustomErrorType("Unable to insert this review, company or client doesn;t exist."), HttpStatus.CONFLICT);


    }
}
