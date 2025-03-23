package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.GroupDto;
import com.exchanger.publisher.dto.PostDto;
import com.exchanger.publisher.dto.UserForGroupDto;
import com.exchanger.publisher.model.*;
import com.exchanger.publisher.repository.UserGroupRepo;
import com.exchanger.publisher.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<GroupDto> getGroupsData(
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

        return groups.stream().map(group -> new GroupDto(group, null, false)).toList();
    }

    @GetMapping("/{groupId}")
    public String getGroup(Model model, @PathVariable("groupId") long groupId) {
        LOGGER.info("Received a GET request to url: /groups/{}", groupId);

        Group group = groupService.findById(groupId);
        if (group == null) {
            throw new EntityNotFoundException("Group with id=" + groupId + " not found");
        }

        Map<Long, UserRole> userGroups = userGroupRepo.findAllByIdGroupId(groupId)
                .stream()
                .collect(Collectors.toMap(ug -> ug.getId().getUserId(), UserGroup::getRole));
        List<UserForGroupDto> members = group.getMembers()
                .stream()
                .map(user -> new UserForGroupDto(user.getId(), user.getUsername(), userGroups.get(user.getId())))
                .toList();

        model.addAttribute("group", new GroupDto(group, members, true));

        return "groups/group";
    }
}
