package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.model.Post;
import com.exchanger.publisher.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountDto {

    private long id;
    private String username;
    private String aboutMyself;
    private List<String> roles = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Group> createdGroups = new ArrayList<>();

    public AccountDto() {
    }

    public AccountDto(long id, String username, String aboutMyself, List<String> roles) {
        this.id = id;
        this.username = username;
        this.aboutMyself = aboutMyself;
        this.roles = roles;
    }

    public AccountDto(User user, List<Group> createdGroups) {
        this(user.getId(), user.getUsername(), user.getAboutMyself(), user.getRoles());
        this.posts = user.getPosts();
        this.groups = user.getGroups();
        this.createdGroups = createdGroups;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getAboutMyself() {
        return aboutMyself;
    }

    public void setAboutMyself(String aboutMyself) {
        this.aboutMyself = aboutMyself;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(List<Group> createdGroups) {
        this.createdGroups = createdGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto accountDto = (AccountDto) o;
        return id == accountDto.id && Objects.equals(username, accountDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", aboutMyself='" + aboutMyself + '\'' +
                ", roles=" + roles +
                '}';
    }
}
