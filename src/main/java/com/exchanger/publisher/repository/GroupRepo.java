package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends ListCrudRepository<Group, Long> {
    List<Group> findAllByCreatorId(long creatorId);

    List<Group> findAll(Pageable pageable);

    List<Group> findAllByNameLike(String name, Pageable pageable);

    List<Group> findAllByNameLike(String name);
}
