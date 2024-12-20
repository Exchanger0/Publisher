package com.exchanger.publisher.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_group")
public class UserGroup {
    @Id
    @Column(name = "user_id")
    private long userId;
    @Id
    @Column(name = "group_id")
    private long groupId;

    public UserGroup() {
    }

    public UserGroup(long userId, long groupId) {
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
}
