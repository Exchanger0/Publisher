package com.exchanger.publisher.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private Group group;
    private String title;
    private String content;
    private LocalDate creationDate;
    private List<String> tags;
    @OneToMany
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Views> views;
    @OneToMany
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Like> likes;
    @OneToMany
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Dislike> dislikes;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post() {
    }

    public Post(User author, Group group, String title, String content, LocalDate creationDate, List<String> tags) {
        this.author = author;
        this.group = group;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.tags = tags;
        views = new ArrayList<>();
        likes = new ArrayList<>();
        dislikes = new ArrayList<>();
        comments = new ArrayList<>();
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

    public List<Views> getViews() {
        return views;
    }

    public void setViews(List<Views> views) {
        this.views = views;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Dislike> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<Dislike> dislikes) {
        this.dislikes = dislikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(author, post.author) && Objects.equals(group, post.group) && Objects.equals(title, post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, group, title);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", tags=" + tags +
                ", views=" + views +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
