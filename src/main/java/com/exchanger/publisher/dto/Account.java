package com.exchanger.publisher.dto;


import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.model.User;

import java.util.ArrayList;
import java.util.List;

public class Account extends UserMini {
    protected List<PostMini> posts;
    protected List<GroupMini> groups;
    protected List<GroupMini> createdGroups;

    public Account(long id, String username, String aboutMyself, List<PostMini> posts, List<GroupMini> groups, List<GroupMini> createdGroups) {
        super(id, username, aboutMyself);
        this.posts = posts;
        this.groups = groups;
        this.createdGroups = createdGroups;
    }

    public Account(User user) {
        super(user);
        this.posts = user.getPosts().stream().map(PostMini::new).toList();
        this.groups = new ArrayList<>();
        this.createdGroups = new ArrayList<>();
        for (Group group : user.getGroups()) {
            if (group.getCreator().equals(user)) {
                this.createdGroups.add(new GroupMini(group));
            }else {
                this.groups.add(new GroupMini(group));
            }
        }
    }

    public List<PostMini> getPosts() {
        return posts;
    }

    public void setPosts(List<PostMini> posts) {
        this.posts = posts;
    }

    public List<GroupMini> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupMini> groups) {
        this.groups = groups;
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
