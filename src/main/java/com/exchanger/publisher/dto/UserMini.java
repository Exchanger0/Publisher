package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.User;

import java.util.Objects;

public class UserMini {
    protected long id;
    protected String username;
    protected String aboutMyself;

    public UserMini(long id, String username, String aboutMyself) {
        this.id = id;
        this.username = username;
        this.aboutMyself = aboutMyself;
    }

    public UserMini(User user) {
        Objects.requireNonNull(user);
        this.id = user.getId();
        this.username = user.getUsername();
        this.aboutMyself = user.getAboutMyself();
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

    public String getAboutMyself() {
        return aboutMyself;
    }

    public void setAboutMyself(String aboutMyself) {
        this.aboutMyself = aboutMyself;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMini userMini = (UserMini) o;
        return id == userMini.id && Objects.equals(username, userMini.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "UserMini{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
