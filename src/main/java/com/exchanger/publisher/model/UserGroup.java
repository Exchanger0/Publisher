package com.exchanger.publisher.model;

import com.exchanger.publisher.model.key.UserGroupId;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_group")
public class UserGroup {
    @EmbeddedId
    private UserGroupId id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    public UserGroup() {
    }

    public UserGroup(long userId, long groupId, UserRole role) {
        id = new UserGroupId(userId, groupId);
        this.role = role;
    }

    public UserGroupId getId() {
        return id;
    }

    public void setId(UserGroupId id) {
        this.id = id;
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
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(id, userGroup.id) && role == userGroup.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
