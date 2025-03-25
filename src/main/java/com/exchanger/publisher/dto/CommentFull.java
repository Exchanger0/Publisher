package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Comment;


public class CommentFull extends CommentMini{
    protected PostMini post;
    protected CommentMini parent;

    public CommentFull() {
    }

    public CommentFull(long id, String content, UserMini author, PostMini post, CommentMini parent) {
        super(id, content, author);
        this.post = post;
        this.parent = parent;
    }

    public CommentFull(Comment comment) {
        super(comment);
        this.post = new PostMini(comment.getPost());
        if (comment.getParent() != null) {
            this.parent = new CommentMini(comment.getParent());
        }
    }

    public PostMini getPost() {
        return post;
    }

    public void setPost(PostMini post) {
        this.post = post;
    }

    public CommentMini getParent() {
        return parent;
    }

    public void setParent(CommentMini parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "CommentFull{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
