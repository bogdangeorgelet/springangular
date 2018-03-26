package com.example.springangularapp.controller;

import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody CompanyDto newUser) {
        System.out.println("A intrat la register");
        CompanyEntity user = companyService.findByEmail(newUser.getEmail());
        if (user != null) {
            logger.error("username Already exist " + newUser.getEmail());
            return new ResponseEntity("username Already exist", HttpStatus.CONFLICT);
        }
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setEmail(newUser.getEmail());
        companyEntity.setName(newUser.getName());
        companyEntity.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        CompanyEntity saved = companyService.save(companyEntity);
        return new ResponseEntity<>(saved.toDto(), HttpStatus.CREATED);
    }

    @RequestMapping("/login")
    public Principal user(Principal principal) {
        System.out.println("A intrat la login");
        logger.info("user logged " + principal);
        return principal;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginPost(@RequestBody CompanyDto companyDto) {
        System.out.println("login");
        System.out.println("user:" + companyDto.getEmail() + " parola:" + companyDto.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(companyDto.getEmail(), companyDto.getPassword());
        String email = token.getName();
        logger.info("Emailul dupa auth :" + email + " company:" + companyService.findByEmail(email));
        CompanyEntity companyEntity = email == null ? null : companyService.findByEmail(email);
        if (companyEntity == null)
            return new ResponseEntity<>("Invalid email", HttpStatus.NOT_FOUND);
        String password = companyEntity.getPassword();
        boolean passwordMatch = bCryptPasswordEncoder.matches(token.getCredentials().toString(), password);
        if (!passwordMatch)
            return new ResponseEntity<>("Invalid password", HttpStatus.NOT_FOUND);

//        CompanyEntity companyEntity = companyService.findByEmailAndPassword(companyDto.getEmail(), companyDto.getPassword());
//        if (companyEntity != null) {
//            return ResponseEntity.ok(companyEntity.toDto());
//        } else
//            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(companyEntity.toDto());
    }
}
