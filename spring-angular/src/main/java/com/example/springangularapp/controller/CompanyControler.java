package com.example.springangularapp.controller;

import com.example.springangularapp.Validator.UserValidator;
import com.example.springangularapp.service.CompanyService;
import com.example.springangularapp.service.SecurityService;
import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CompanyControler {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<List<CompanyDto>> listAllCompanies() {
        List<CompanyEntity> companies = companyService.getAllCompanies();
        if (companies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CompanyEntity.toDtos(companies));
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable("id") int id) {
        logger.info("Fetching CompanyEntity with id {}", id);
        Optional<CompanyEntity> company = companyService.findCompanyById(id);
        if (!company.isPresent()) {
            logger.error("ClientEntity with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company.get().toDto());
    }




    ////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new CompanyEntity());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") CompanyEntity userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        companyService.save(userForm);
        securityService.autologin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

}
