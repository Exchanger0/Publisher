package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PostMini {
    protected long id;
    protected String title;
    protected String content;
    protected LocalDate creationDate;
    protected List<String> tags;
    protected long likes;
    protected long dislikes;
    protected long views;

    public PostMini() {
    }

    public PostMini(long id, String title, String content, LocalDate creationDate, List<String> tags, long likes, long dislikes, long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.tags = tags;
        this.likes = likes;
        this.dislikes = dislikes;
        this.views = views;
    }

    public PostMini(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(), post.getCreationDate(), post.getTags(), post.getLikes().size(), post.getDislikes().size(), post.getViews().size());
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostMini postMini = (PostMini) o;
        return id == postMini.id && Objects.equals(title, postMini.title) && Objects.equals(content, postMini.content) && Objects.equals(creationDate, postMini.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, creationDate);
    }

    @Override
    public String toString() {
        return "PostMini{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
