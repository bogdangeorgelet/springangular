package com.example.springangularapp.controller;

import com.example.springangularapp.Util.CustomErrorType;
import com.example.springangularapp.entity.Client;
import com.example.springangularapp.entity.Company;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;


@Controller
public class CompanyControler {

    @Autowired
    CompanyRepository companyRepository;

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestBody Company company, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Client : {}", company);

        if (companyRepository.findByEmail(company.getEmail()) != null) {
            logger.error("Unable to create. A company with email {} already exist", company.getEmail());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with company " +
                    company.getEmail() + " already exist."), HttpStatus.CONFLICT);
        }
        companyRepository.save(company);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/company/{id}").buildAndExpand(company.getId()).toUri());
        return new ResponseEntity<String>("S-a inregitrat  cu succes",HttpStatus.CREATED);

    }
}
