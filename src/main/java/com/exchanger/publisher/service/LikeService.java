package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Like;
import com.exchanger.publisher.model.key.LDVID;
import com.exchanger.publisher.repository.LikeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeService extends BaseService<Like, LDVID, LikeRepo> {

    @PersistenceContext
    private EntityManager entityManager;

    public LikeService(LikeRepo repository) {
        super(repository);
    }


    public boolean like(long postId, long userId) {
        LDVID ldvid = new LDVID(postId, userId);

        if (existsById(ldvid)) {
            deleteById(ldvid);
            return false;
        }else {
            save(new Like(ldvid));
            return true;
        }
    }

    @Override
    public void deleteById(LDVID id) {
        Session session = entityManager.unwrap(Session.class);
        session.createMutationQuery("DELETE Like l WHERE l.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void delete(Like entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends LDVID> ids) {
        for (LDVID id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<Like> entities) {
        for (Like like : entities) {
            delete(like);
        }
    }
}
