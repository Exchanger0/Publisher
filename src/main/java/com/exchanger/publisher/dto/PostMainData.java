package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Post;

import java.time.LocalDate;
import java.util.Objects;

public class PostMainData {
    private long id;
    private String title;
    private String content;
    private LocalDate creationDate;
    private long views;
    private long likes;
    private long dislikes;

    public PostMainData(long id, String title, String content, LocalDate creationDate, long views, long likes, long dislikes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.views = views;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public PostMainData(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(), post.getCreationDate(), post.getViews().size(),
                post.getLikes().size(), post.getDislikes().size());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        PostMainData postDto = (PostMainData) o;
        return id == postDto.id && Objects.equals(title, postDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
