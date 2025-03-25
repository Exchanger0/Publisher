package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Post;

import java.time.LocalDate;
import java.util.List;

public class PostFull extends PostMini{
    protected UserMini author;
    protected GroupMini group;

    public PostFull(long id, String title, String content, LocalDate creationDate, long likes, long dislikes, long views, List<String> tags, UserMini author, GroupMini group) {
        super(id, title, content, creationDate, tags, likes, dislikes, views);
        this.tags = tags;
        this.author = author;
        this.group = group;
    }

    public PostFull(Post post) {
        super(post);
        this.author = new UserMini(post.getAuthor());
        if (post.getGroup() != null) {
            this.group = new GroupMini(post.getGroup());
        }
    }

    public UserMini getAuthor() {
        return author;
    }

    public void setAuthor(UserMini author) {
        this.author = author;
    }

    public GroupMini getGroup() {
        return group;
    }

    public void setGroup(GroupMini group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "PostFull{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
