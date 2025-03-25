package com.exchanger.publisher.dto;

import com.exchanger.publisher.model.Group;

import java.util.Objects;

public class GroupMini {
    protected long id;
    protected String name;
    protected String description;

    public GroupMini(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public GroupMini(Group group) {
        Objects.requireNonNull(group);
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMini groupMini = (GroupMini) o;
        return id == groupMini.id && Objects.equals(name, groupMini.name) && Objects.equals(description, groupMini.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "GroupMini{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
