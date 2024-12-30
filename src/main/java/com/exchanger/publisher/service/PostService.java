package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Post;
import com.exchanger.publisher.repository.PostRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService extends BaseService<Post, Long, PostRepo> {

    private final LikeService likeService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PostService(PostRepo repository, LikeService likeService) {
        super(repository);
        this.likeService = likeService;
    }

    @Transactional(readOnly = true)
    public List<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void refresh(Post post) {
        entityManager.refresh(post);
    }
}
