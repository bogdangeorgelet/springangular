package com.example.springangularapp.springangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Controller
public class CompanyControler {

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public String processEvent(@ModelAttribute("event") Event event) {
        System.out.println(event);

        return "redirect:index.html";
    }
}
