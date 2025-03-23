package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.model.Post;
import com.exchanger.publisher.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PostDto {

    private long id;
    private UserDto author;
    private GroupDto group;
    private String title;
    private String content;
    private LocalDate creationDate;
    private List<String> tags;
    private long views;
    private long likes;
    private long dislikes;


    public PostDto(long id, UserDto author, GroupDto group, String title, String content, LocalDate creationDate, List<String> tags, long views, long likes, long dislikes) {
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

    public PostDto(Post post) {
        this(post.getId(), new UserDto(post.getAuthor(), false),
                post.getGroup() != null ? new GroupDto(post.getGroup(), null, false) : null,
                post.getTitle(), post.getContent(), post.getCreationDate(), post.getTags(),
                post.getViews().size(), post.getLikes().size(), post.getDislikes().size());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
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
