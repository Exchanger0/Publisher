package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.Account;
import com.exchanger.publisher.model.User;
import com.exchanger.publisher.service.GroupService;
import com.exchanger.publisher.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

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
        LOGGER.info("User id={}", user.getId());

        model.addAttribute("user", new Account(
                userService.findByIdWithEntities(user.getId()),
                groupService.findAllByCreatorId(user.getId())
        ));

        return "account/account";
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

    @DeleteMapping
    public String delete(@AuthenticationPrincipal User user) {
        LOGGER.info("Received a DELETE request to url: /account");
        LOGGER.info("Delete user with id={}", user.getId());

        userService.delete(user);

        return "redirect:/logout";
    }
}
