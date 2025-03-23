package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.UserRole;

import java.util.Objects;

public class UserForGroupDto {
    private long id;
    private String username;
    private UserRole role;

    public UserForGroupDto(long id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForGroupDto that = (UserForGroupDto) o;
        return id == that.id && Objects.equals(username, that.username) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role);
    }
}
