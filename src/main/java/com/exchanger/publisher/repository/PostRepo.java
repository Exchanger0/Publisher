package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends ListCrudRepository<Post, Long> {
    List<Post> findAll(Pageable pageable);

    List<Post> findByAuthorId(long id);
}
