package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.UserDto;
import com.exchanger.publisher.model.User;
import com.exchanger.publisher.service.GroupService;
import com.exchanger.publisher.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public AccountController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getInfo(@AuthenticationPrincipal User user, Model model) {
        LOGGER.info("Received a GET request to url: /account");
        LOGGER.info("User={}", user);
        LOGGER.info("User.posts={}", user.getPosts());

        model.addAttribute("user", new UserDto(user, groupService.findByCreatorId(user.getId())));

        return "account";
    }

    @PutMapping
    public String updateAbout(@RequestParam("about") String about, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a PUT request to url: /account");

        if (!about.equals(user.getAboutMyself())) {
            user.setAboutMyself(about);
            userService.save(user);
        }

        return "redirect:/account";
    }
}
