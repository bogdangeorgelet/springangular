package com.example.springangularapp.controller;

import com.example.springangularapp.Util.CustomErrorType;
import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@Controller
public class CompanyControler {

    @Autowired
    CompanyRepository companyRepository;

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

//-------------------------------register companyEntity-----------------------------

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody CompanyDto companyDto, UriComponentsBuilder ucBuilder) {
        logger.info("Creating ClientEntity : {}", companyDto);

        if (companyRepository.findByEmail(companyDto.getEmail()) != null) {
            logger.error("Unable to create. A companyEntity with email {} already exist", companyDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.update(companyDto);
        companyRepository.save(companyEntity);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    //-------------------------- get ALl companies---------------------------------

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<List<CompanyDto>> listAllCompanies() {
        List<CompanyEntity> companies = (List<CompanyEntity>) companyRepository.findAll();
        if (companies.isEmpty()) {
            return ResponseEntity.noContent().build();
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return ResponseEntity.ok(CompanyEntity.toDtos(companies));
    }

    //-------------------get companyEntity by id--------------------
    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable("id") int id) {
        logger.info("Fetching CompanyEntity with id {}", id);
        Optional<CompanyEntity> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            logger.error("ClientEntity with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company.get().toDto());
    }
}
