package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Views;
import com.exchanger.publisher.model.key.LDVID;
import com.exchanger.publisher.repository.ViewsRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ViewsService extends BaseService<Views, LDVID, ViewsRepo> {

    @PersistenceContext
    private EntityManager entityManager;

    public ViewsService(ViewsRepo repository) {
        super(repository);
    }

    public void view(long postId, long userId) {
        LDVID ldvid = new LDVID(postId, userId);

        if (!existsById(ldvid)) {
            save(new Views(ldvid));
        }
    }
}
