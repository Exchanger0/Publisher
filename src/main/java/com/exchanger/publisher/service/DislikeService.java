package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Dislike;
import com.exchanger.publisher.model.key.LDVID;
import com.exchanger.publisher.repository.DislikeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DislikeService extends BaseService<Dislike, LDVID, DislikeRepo> {

    @PersistenceContext
    private EntityManager entityManager;

    public DislikeService(DislikeRepo repository) {
        super(repository);
    }

    public boolean dislike(long postId, long userId) {
        LDVID ldvid = new LDVID(postId, userId);

        if (existsById(ldvid)) {
            deleteById(ldvid);
            return false;
        }else {
            save(new Dislike(ldvid));
            return true;
        }
    }

    @Override
    public void deleteById(LDVID id) {
        Session session = entityManager.unwrap(Session.class);
        session.createMutationQuery("DELETE Dislike l WHERE l.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void delete(Dislike entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends LDVID> ids) {
        for (LDVID id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<Dislike> entities) {
        for (Dislike like : entities) {
            delete(like);
        }
    }
}
