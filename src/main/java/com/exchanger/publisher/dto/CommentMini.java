package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Comment;

import java.util.Objects;

public class CommentMini {
    protected long id;
    protected String content;
    protected UserMini author;

    public CommentMini() {
    }

    public CommentMini(long id, String content, UserMini author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public CommentMini(Comment comment) {
        Objects.requireNonNull(comment);
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = new UserMini(comment.getAuthor());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserMini getAuthor() {
        return author;
    }

    public void setAuthor(UserMini author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentMini that = (CommentMini) o;
        return id == that.id && Objects.equals(content, that.content) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, author);
    }

    @Override
    public String toString() {
        return "CommentMini{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
