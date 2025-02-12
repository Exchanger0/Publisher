package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends ListCrudRepository<Post, Long> {
    List<Post> findAll(Pageable pageable);

    List<Post> findByAuthorId(long id);

    @Query(value = """
            SELECT * FROM post
            WHERE title LIKE %:q% or EXISTS (SELECT 1 FROM unnest(tags) AS tag WHERE tag LIKE %:q%)""",
            nativeQuery = true)
    List<Post> findAllByTitleOrTag(@Param("q") String q, Pageable pageable);

    @Query(value = """
            SELECT * FROM post
            WHERE title LIKE %:q% or EXISTS (SELECT 1 FROM unnest(tags) AS tag WHERE tag LIKE %:q%)""",
            nativeQuery = true)
    List<Post> findAllByTitleOrTag(@Param("q") String q);
}
