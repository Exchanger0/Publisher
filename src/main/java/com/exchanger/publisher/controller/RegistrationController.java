package com.exchanger.publisher.controller;

import com.exchanger.publisher.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegistrationController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String getRegistrationForm(@RequestParam(value = "error", required = false) String error, Model model) {
        LOGGER.info("Received a GET request to url: /registration");

        model.addAttribute("error", error);
        return "registration";
    }

    @PostMapping
    public String registration(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
        LOGGER.info("Received a POST request to url: /registration");
        LOGGER.info("Registration: username={}", username);

        if (userService.existsByUsername(username))
            return "redirect:/registration?error=true";

        userService.save(username, password);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/posts";
    }
}
