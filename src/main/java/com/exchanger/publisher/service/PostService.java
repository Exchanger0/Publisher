package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Post;
import com.exchanger.publisher.repository.PostRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

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

    @Transactional(readOnly = true)
    public List<Post> findAllByTitleOrTags(String q) {
        return repository.findAllByTitleOrTag(q);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllByTitleOrTags(String q, Pageable pageable) {
        return repository.findAllByTitleOrTag(q, pageable);
    }

    public void refresh(Post post) {
        entityManager.refresh(post);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        Session session = entityManager.unwrap(Session.class);
        //отсоединение поста от групп, созданных пользователем
        session.createMutationQuery("""
                UPDATE Post p SET p.group = NULL
                WHERE p.group IN (
                    SELECT gr FROM Group gr WHERE gr.creator.id in :ids
                )
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление лайков постов
        session.createMutationQuery("""
                DELETE Like l WHERE l.id.postId in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление дизлайков постов
        session.createMutationQuery("""
                DELETE Dislike dl WHERE dl.id.postId in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление просмотров постов
        session.createMutationQuery("""
                DELETE Views v WHERE v.id.postId in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление комментариев постов
        session.createMutationQuery("""
                DELETE Comment c WHERE c.post.id in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление постов
        session.createMutationQuery("""
                DELETE Post p WHERE p.id in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        deleteAllById(List.of(id));
    }

    @Override
    public void delete(Post entity) {
        deleteAllById(List.of(entity.getId()));
    }

    @Override
    public void deleteAll(Iterable<Post> entities) {
        deleteAllById(StreamSupport.stream(entities.spliterator(), false).map(Post::getId).toList());
    }

    public void deleteByAuthorId(long id) {
        deleteAllById(repository.findByAuthorId(id).stream().map(Post::getId).toList());
    }
}
