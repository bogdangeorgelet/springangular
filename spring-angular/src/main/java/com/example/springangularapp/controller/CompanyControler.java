package com.example.springangularapp.controller;

import com.example.springangularapp.Validator.UserValidator;
import com.example.springangularapp.service.CompanyService;
import com.example.springangularapp.service.EmailService;
import com.example.springangularapp.service.SecurityService;

import java.util.Map;

import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.repository.CompanyRepository;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class CompanyControler {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private SecurityService securityService;
    //
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Environment environment;

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
// Return registration form template
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, CompanyDto companyDto) {
        modelAndView.addObject("user", companyDto);
        modelAndView.setViewName("register");
        return modelAndView;
    }


// varianta fara email

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public ModelAndView createNewUser(@Valid CompanyDto companyDto, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        CompanyEntity companyExist = companyService.findByEmail(companyDto.getEmail());
//        if (companyExist != null) {
//            bindingResult
//                    .rejectValue("email", "error.user",
//                            "There is already a user registered with the email provided");
//        }
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("registration");
//        } else {
//            CompanyEntity companyEntity = new CompanyEntity();
//            companyEntity.setPassword(companyDto.getPassword());
//            companyEntity.setEmail(companyDto.getEmail());
//            companyEntity.setName(companyDto.getName());
//            companyService.save(companyEntity);
//            modelAndView.addObject("successMessage", "User has been registered successfully");
//            modelAndView.addObject("user", new CompanyDto());
//            modelAndView.setViewName("registration");
//
//        }
//        return modelAndView;
//    }
//
//    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
//    public ModelAndView login() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }

    // varianta cu email
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid CompanyDto companyDto, BindingResult bindingResult, HttpServletRequest request) {

        // Lookup user in database by e-mail
        CompanyEntity userExists = companyService.findByEmail(companyDto.getEmail());

        System.out.println(userExists);

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
            System.out.println("exista deja acest user");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else { // new user so we create user and send confirmation e-mail
            System.out.println("se creaza un user nou");
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setName(companyDto.getName());
            companyEntity.setEmail(companyDto.getEmail());
            // Disable user until they click on confirmation link in email
            companyEntity.setEnabled(false);

            // Generate random 36-character string token for confirmation link
            companyEntity.setConfirmationToken(UUID.randomUUID().toString());

            companyService.save(companyEntity);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(companyDto.getEmail());
            registrationEmail.setSubject("Registration Confirmation");
            String port = environment.getProperty("local.server.port");
            System.out.println("port:" + port);
            registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                    + appUrl + ":" + port + "/confirm?token=" + companyEntity.getConfirmationToken());
            registrationEmail.setFrom("noreply@domain.com");

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + companyDto.getEmail());
            modelAndView.setViewName("register");
        }

        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        CompanyEntity company = companyService.findByConfirmationToken(token);

        if (company == null) { // No token found in DB
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else { // Token found
            modelAndView.addObject("confirmationToken", company.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {

        modelAndView.setViewName("confirm");

        Zxcvbn passwordCheck = new Zxcvbn();

        Strength strength = passwordCheck.measure(requestParams.get("password").toString());

//        if (strength.getScore() < 3) {
//            bindingResult.reject("password");
//
//            redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
//
//            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
//            System.out.println(requestParams.get("token"));
//            return modelAndView;
//        }

        // Find the user associated with the reset token
        CompanyEntity companyEntity = companyService.findByConfirmationToken(requestParams.get("token").toString());

        // Set new password
        companyEntity.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password").toString()));

        // Set user to enabled
        companyEntity.setEnabled(true);

        // Save user
        companyService.save(companyEntity);

        modelAndView.addObject("successMessage", "Your password has been set!");
        return modelAndView;
    }


    //    ///varianta veche
//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String registration(@ModelAttribute("userForm") CompanyEntity userForm, BindingResult bindingResult) {
//        userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "error";
//        }
//        companyService.save(userForm);
//        securityService.autologin(userForm.getEmail(), userForm.getPassword());
//
//        return "redirect:/welcome";
//    }
    /// varianta veche
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}

