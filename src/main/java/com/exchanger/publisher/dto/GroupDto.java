package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Group;

import java.util.List;
import java.util.Objects;

public class GroupDto {
    private long id;
    private String name;
    private UserDto creator;
    private String description;
    private List<PostDto> posts;
    private List<UserForGroupDto> members;

    public GroupDto(long id, String name, UserDto creator, String description, List<PostDto> posts, List<UserForGroupDto> members) {
        init(id, name, creator, description, posts, members);
    }

//    public GroupDto(long id, String name, UserDto creator, String description) {
//        init(id, name, creator, description, null, null);
//    }

    public GroupDto(Group group, List<UserForGroupDto> members, boolean withDepends) {
        if (withDepends) {
            init(group.getId(), group.getName(), new UserDto(group.getCreator(), false), group.getDescription(),
                group.getPosts().stream().map(PostDto::new).toList(),
                members);
        } else {
            init(group.getId(), group.getName(), new UserDto(group.getCreator(), false), group.getDescription(),
                    null, null);
        }
    }

    public void init(long id, String name, UserDto creator, String description, List<PostDto> posts, List<UserForGroupDto> members) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.posts = posts;
        this.members = members;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public List<UserForGroupDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserForGroupDto> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDto group = (GroupDto) o;
        return id == group.id && Objects.equals(name, group.name) && Objects.equals(creator, group.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creator);
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
