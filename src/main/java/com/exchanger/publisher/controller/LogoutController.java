package com.exchanger.publisher.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @Autowired
    private SecurityContextLogoutHandler handler;

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        handler.logout(request, null, null);
        return "redirect:/login";
    }
}
