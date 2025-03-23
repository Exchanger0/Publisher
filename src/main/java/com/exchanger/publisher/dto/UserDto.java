package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private long id;
    private String username;
    private String password;
    private String aboutMyself;
    private List<String> roles = new ArrayList<>();
    private List<PostDto> posts = new ArrayList<>();
    private List<GroupDto> groups = new ArrayList<>();

    public UserDto(long id, String username, String password, String aboutMyself) {
        init(id, username, password, aboutMyself, null, null, null);
    }

    public UserDto(long id, String username, String password, String aboutMyself, List<String> roles,
                   List<PostDto> posts, List<GroupDto> groups) {
        init(id, username, password, aboutMyself, roles, posts, groups);
    }

    public UserDto(User user, boolean withDepends) {
        if (withDepends) {
            init(user.getId(), user.getUsername(), user.getPassword(), user.getAboutMyself(), user.getRoles(),
                    user.getPosts().stream().map(PostDto::new).toList(),
                    user.getGroups().stream().map(group -> new GroupDto(group, null, false)).toList());
        } else {
            init(user.getId(), user.getUsername(), user.getPassword(), user.getAboutMyself(), user.getRoles(),
                    null, null);
        }
    }

    private void init(long id, String username, String password, String aboutMyself, List<String> roles,
                      List<PostDto> posts, List<GroupDto> groups) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.aboutMyself = aboutMyself;
        this.roles = roles;
        this.posts = posts;
        this.groups = groups;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDto> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", aboutMyself='" + aboutMyself + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
