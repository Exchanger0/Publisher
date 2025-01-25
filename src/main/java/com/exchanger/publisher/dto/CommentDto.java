package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Comment;

import java.util.Objects;

public class CommentDto {
    private long id;
    private String content;
    private String authorUsername;
    private long postId;
    private long parentId;

    public CommentDto() {
    }

    public CommentDto(long id, String content, String authorUsername, long postId, long parentId) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.postId = postId;
        this.parentId = parentId;
    }

    public CommentDto(Comment comment) {
        this(comment.getId(), comment.getContent(), comment.getAuthor().getUsername(), comment.getPost().getId(),
                comment.getParent() != null ? comment.getParent().getId() : -1);
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

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return id == that.id && postId == that.postId && parentId == that.parentId && Objects.equals(content, that.content) && Objects.equals(authorUsername, that.authorUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, authorUsername, postId, parentId);
    }
}
