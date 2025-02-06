package com.exchanger.publisher.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public String handle(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "main/error";
    }
}
