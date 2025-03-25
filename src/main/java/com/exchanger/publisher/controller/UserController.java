package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.Account;
import com.exchanger.publisher.service.GroupService;
import com.exchanger.publisher.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final GroupService groupService;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/{userId}")
    public String getUser(Model model, @PathVariable("userId") long userId) {
        LOGGER.info("Received a GET request to url: /users/{}", userId);

        if (!userService.existsById(userId)) {
            throw new EntityNotFoundException("User with id=" + userId + " not found");
        }
        model.addAttribute("user", new Account(
                userService.findByIdWithEntities(userId),
                groupService.findAllByCreatorId(userId)
        ));

        return "users/user";
    }
}
