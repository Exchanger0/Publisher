package com.exchanger.publisher.controller;

import com.exchanger.publisher.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegistrationForm(@RequestParam(value = "error", required = false) String error, Model model) {
        LOGGER.info("Received a GET request to url: /registration");

        model.addAttribute("error", error);
        return "registration";
    }

    @PostMapping
    public String registration(@RequestParam("username") String username, @RequestParam("password") String password) {
        LOGGER.info("Received a POST request to url: /registration");
        LOGGER.info("Registration: username={}", username);

        if (userService.existsByUsername(username))
            return "redirect:/registration?error=true";

        userService.save(username, password);

        return "redirect:/posts";
    }
}
