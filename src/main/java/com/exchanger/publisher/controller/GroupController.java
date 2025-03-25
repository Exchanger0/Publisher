package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.GroupFull;
import com.exchanger.publisher.dto.UserMini;
import com.exchanger.publisher.model.*;
import com.exchanger.publisher.repository.UserGroupRepo;
import com.exchanger.publisher.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final UserGroupRepo userGroupRepo;

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public GroupController(GroupService groupService, UserGroupRepo userGroupRepo) {
        this.groupService = groupService;
        this.userGroupRepo = userGroupRepo;
    }

    @GetMapping
    public String getGroupsPage() {
        LOGGER.info("Received a GET request to url: /groups");

        return "groups/groups";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<GroupFull> getGroupsData(
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
            @RequestParam(value = "q", required = false, defaultValue = "__none__") String q) {
        LOGGER.info("Received a GET request to url: /groups/data");
        LOGGER.info("start={}, amount={}, q={}", start, amount, q);

        List<Group> groups;
        if (!q.isEmpty() && !q.equals("__none__"))
            groups = groupService.findAllByNameLike("%" + q + "%", PageRequest.of(start, amount));
        else
            groups = groupService.findAll(PageRequest.of(start, amount));

        return groups.stream().map(group -> new GroupFull(group, null)).toList();
    }

    @GetMapping("/{groupId}")
    public String getGroup(Model model, @PathVariable("groupId") long groupId) {
        LOGGER.info("Received a GET request to url: /groups/{}", groupId);

        Group group = groupService.findById(groupId);
        if (group == null) {
            throw new EntityNotFoundException("Group with id=" + groupId + " not found");
        }

        List<UserGroup> userGroups = userGroupRepo.findAllByIdGroupId(groupId);
        Map<UserMini, UserRole> members = new HashMap<>();
        for (UserGroup ug : userGroups) {
            group.getMembers()
                    .stream()
                    .filter(u -> u.getId() == ug.getId().getUserId())
                    .findFirst()
                    .ifPresent(value -> members.put(new UserMini(value), ug.getRole()));
        }

        model.addAttribute("group", new GroupFull(group, members));

        return "groups/group";
    }
}
