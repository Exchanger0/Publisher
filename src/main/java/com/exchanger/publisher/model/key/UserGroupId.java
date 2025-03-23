package com.exchanger.publisher.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UserGroupId {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "group_id")
    private long groupId;

    public UserGroupId() {}

    public UserGroupId(long userId, long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupId that = (UserGroupId) o;
        return userId == that.userId && groupId == that.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }
}
