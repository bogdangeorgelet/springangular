package com.example.springangularapp.controller;

import com.example.springangularapp.dto.ReviewDto;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.entity.ReviewEntity;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.repository.CompanyRepository;
import com.example.springangularapp.service.ReviewService;
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
    ReviewService reviewService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ClientRepository clientRepository;


    @RequestMapping(value = "/reviews/company/{company_id}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewDto>> listAllReviewsByCompanyId(@PathVariable int company_id) {
        List<ReviewEntity> reviewEntities = reviewService.findReviewsByCompanyEntityId(company_id);
        if (reviewEntities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ReviewEntity.toDtos(reviewEntities));
    }


    @RequestMapping(value = "/reviews/client/{client_id}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewDto>> listAllReviewsByClientId(@PathVariable int client_id) {
        System.out.println("client id:" + client_id);
        List<ReviewEntity> reviewEntities = reviewService.findReviewsByClientId(client_id);
        if (reviewEntities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ReviewEntity.toDtos(reviewEntities));
    }


    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewDto>> listAllReviews() {
        List<ReviewEntity> reviews = reviewService.getAllReviews();
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ReviewEntity.toDtos(reviews));
    }


    @RequestMapping(value = "/review/", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody ReviewDto reviewDto, @RequestParam int company_id, @RequestParam int client_id, UriComponentsBuilder ucBuilder) {
        logger.info("Creating reviewDto : {}", reviewDto);
        System.out.println("companyEntity id:" + company_id);
        System.out.println("clieny id:" + client_id);
        Optional<CompanyEntity> company = companyRepository.findById(company_id);
        Optional<ClientEntity> client = clientRepository.findById(client_id);
        if (company.isPresent() && client.isPresent()) {
            ReviewEntity reviewEntity = new ReviewEntity();
            reviewEntity.setClient(client.get());
            reviewEntity.setCompanyEntity(company.get());
            reviewEntity.update(reviewDto);
            reviewService.saveReview(reviewEntity);

//            HttpHeaders headers = new HttpHeaders();
//            headers.setLocation(ucBuilder.path("/api/client/{id}").buildAndExpand(reviewDto.getId()).toUri());

            return ResponseEntity.ok(HttpStatus.CREATED);
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }
}
