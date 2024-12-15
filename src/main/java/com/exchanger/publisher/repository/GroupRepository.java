package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Group;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface GroupRepository extends ListCrudRepository<Group, Long> {
    List<Group> findByCreatorId(long creatorId);
}
