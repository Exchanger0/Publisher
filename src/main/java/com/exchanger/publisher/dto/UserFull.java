package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.User;

import java.util.List;

public class UserFull extends UserMini{
    protected List<PostMini> posts;
    protected List<GroupMini> groups;

    public UserFull(long id, String username, String aboutMyself, List<PostMini> posts, List<GroupMini> groups) {
        super(id, username, aboutMyself);
        this.posts = posts;
        this.groups = groups;
    }

    public UserFull(User user) {
        super(user);
        this.posts = user.getPosts().stream().map(PostMini::new).toList();
        this.groups = user.getGroups().stream().map(GroupMini::new).toList();
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

    @Override
    public String toString() {
        return "UserFull{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
