package com.exchanger.publisher.dto;


import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.model.User;

import java.util.List;

public class Account extends UserFull{
    protected List<GroupMini> createdGroups;

    public Account(long id, String username,String aboutMyself, List<PostMini> posts, List<GroupMini> groups, List<GroupMini> createdGroups) {
        super(id, username, aboutMyself, posts, groups);
        this.createdGroups = createdGroups;
    }

    public Account(User user, List<Group> createdGroups) {
        super(user);
        this.createdGroups = createdGroups.stream().map(GroupMini::new).toList();
    }

    public List<GroupMini> getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(List<GroupMini> createdGroups) {
        this.createdGroups = createdGroups;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
