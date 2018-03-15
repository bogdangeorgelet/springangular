package com.example.springangularapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;


@Controller
public class CompanyControler {

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public String processEvent(@ModelAttribute("event") Event event) {
        System.out.println(event);

        return "redirect:index.html";
    }
}
