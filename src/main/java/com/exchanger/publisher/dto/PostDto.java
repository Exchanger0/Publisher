package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.model.Post;
import com.exchanger.publisher.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PostDto {

    private long id;
    private User author;
    private Group group;
    private String title;
    private String content;
    private LocalDate creationDate;
    private List<String> tags;
    private long views;
    private long likes;
    private long dislikes;

    public PostDto(Post post) {
        this.id = post.getId();
        this.author = post.getAuthor();
        this.group = post.getGroup();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.creationDate = post.getCreationDate();
        this.tags = post.getTags();
        this.views = post.getViews().size();
        this.likes = post.getLikes().size();
        this.dislikes = post.getDislikes().size();
    }

    public PostDto(long id, User author, Group group, String title, String content, LocalDate creationDate, List<String> tags, long views, long likes, long dislikes) {
        this.id = id;
        this.author = author;
        this.group = group;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.tags = tags;
        this.views = views;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto postDto = (PostDto) o;
        return id == postDto.id && Objects.equals(author, postDto.author) && Objects.equals(group, postDto.group) && Objects.equals(title, postDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, group, title);
    }
}
