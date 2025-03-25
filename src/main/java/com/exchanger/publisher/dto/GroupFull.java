package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.model.UserRole;

import java.util.List;
import java.util.Map;

public class GroupFull extends GroupMini{
    protected UserMini creator;
    protected List<PostMini> posts;
    protected Map<UserMini, UserRole> members;

    public GroupFull(long id, String name, String description, UserMini creator, List<PostMini> posts, Map<UserMini, UserRole> members) {
        super(id, name, description);
        this.creator = creator;
        this.posts = posts;
        this.members = members;
    }

    public GroupFull(Group group, Map<UserMini, UserRole> members) {
        super(group);
        this.creator = new UserMini(group.getCreator());
        this.posts = group.getPosts().stream().map(PostMini::new).toList();
        this.members = members;
    }

    public UserMini getCreator() {
        return creator;
    }

    public void setCreator(UserMini creator) {
        this.creator = creator;
    }

    public List<PostMini> getPosts() {
        return posts;
    }

    public void setPosts(List<PostMini> posts) {
        this.posts = posts;
    }

    public Map<UserMini, UserRole> getMembers() {
        return members;
    }

    public void setMembers(Map<UserMini, UserRole> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupFull{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
