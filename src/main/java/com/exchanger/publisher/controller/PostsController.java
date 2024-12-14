package com.exchanger.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String getHome() {
        LOGGER.info("Received a GET request to url: /posts");

        return "posts/posts";
    }
}
