package com.exchanger.publisher.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LDVID implements Serializable {

    @Column(name = "post_id")
    private long postId;
    @Column(name = "user_id")
    private long userId;

    public LDVID() {
    }

    public LDVID(long postId, long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LDVID ldvid = (LDVID) o;
        return postId == ldvid.postId && userId == ldvid.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }

    @Override
    public String toString() {
        return "LDVID{" +
                "postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}