package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends ListCrudRepository<Comment, Long> {
    List<Comment> findAllByPostIdAndParentIdIsNull(long postId, Pageable pageable);

    List<Comment> findAllByParentId(long parentId, Pageable pageable);
}
