package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Comment;
import com.exchanger.publisher.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService extends BaseService<Comment, Long, CommentRepo> {
    @Autowired
    public CommentService(CommentRepo repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public List<Comment> findTopLevelByPostId(long postId, Pageable pageable) {
        return repository.findAllByPostIdAndParentIdIsNull(postId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllByParentId(long parentId, Pageable pageable) {
        return repository.findAllByParentId(parentId, pageable);
    }
}
