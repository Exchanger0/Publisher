package com.exchanger.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String getLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        LOGGER.info("Received a GET request to url: /login");
        LOGGER.info("Has error={}", error);

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        model.addAttribute("error", error);
        return "auth/login";
    }
}