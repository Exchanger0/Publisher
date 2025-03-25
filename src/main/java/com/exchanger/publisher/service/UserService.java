package com.exchanger.publisher.service;

import com.exchanger.publisher.model.User;
import com.exchanger.publisher.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService extends BaseService<User, Long, UserRepo> {

    private final PostService postService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepo repository, PostService postService) {
        super(repository);
        this.postService = postService;
    }

    public void save(String username, String password) {
        save(new User(username, password, "", List.of("USER_ROLE")));
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        Session session = entityManager.unwrap(Session.class);

        //удаление записи о том, что пользователь член этой группы, созданной пользователем
        session.createMutationQuery("""
                DELETE UserGroup ug WHERE ug.id.groupId IN (
                    SELECT gr.id FROM Group gr WHERE gr.creator.id in :ids
                )
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление записи о том, что пользователь является членом какой-либо группы
        session.createMutationQuery("""
                DELETE UserGroup ug WHERE ug.id.userId in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление групп созданных пользователем
        session.createMutationQuery("""
                DELETE Group gr WHERE gr.creator.id in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
        //удаление постов созданных пользователем
        for (Long id : ids) {
            postService.deleteByAuthorId(id);
        }
        //удаление самого пользователя
        session.createMutationQuery("""
                DELETE User u WHERE u.id in :ids
                """)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        deleteAllById(List.of(id));
    }

    @Override
    public void delete(User entity) {
        deleteAllById(List.of(entity.getId()));
    }

    @Override
    public void deleteAll(Iterable<User> entities) {
        deleteAllById(StreamSupport.stream(entities.spliterator(), false).map(User::getId).toList());
    }

    public User findByIdWithEntities(long id) {
        User user = findById(id);
        Hibernate.initialize(user);
        return user;
    }
}
