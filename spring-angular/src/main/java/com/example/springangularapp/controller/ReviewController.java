package com.example.springangularapp.controller;

import com.example.springangularapp.entity.Review;
import com.example.springangularapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;


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


}
