package com.exchanger.publisher.service;

import com.exchanger.publisher.model.User;
import com.exchanger.publisher.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService extends BaseService<User, Long, UserRepo> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepo repository) {
        super(repository);
    }

    public void save(String username, String password) {
        save(new User(username, password, "", List.of("USER_ROLE")));
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        Session session = entityManager.unwrap(Session.class);

        //отсоединение поста от групп, созданных пользователем
        session.createMutationQuery("""
                UPDATE Post p SET p.group = NULL
                WHERE p.group IN (
                    SELECT gr FROM Group gr WHERE gr.creator.id = :id
                )
                """)
                .setParameter("id", id)
                .executeUpdate();
        //удаление записи о том, что пользователь член этой группы, созданной пользователем
        session.createMutationQuery("""
                DELETE UserGroup ug WHERE ug.groupId IN (
                    SELECT gr.id FROM Group gr WHERE gr.creator.id = :id
                )
                """)
                .setParameter("id", id)
                .executeUpdate();
        //удаление записи о том, что пользователь является членом какой-либо группы
        session.createMutationQuery("""
                DELETE UserGroup ug WHERE ug.userId = :id
                """)
                .setParameter("id", id)
                .executeUpdate();
        //удаление групп созданных пользователем
        session.createMutationQuery("""
                DELETE Group gr WHERE gr.creator.id = :id
                """)
                .setParameter("id", id)
                .executeUpdate();
        //удаление постов созданных пользователем
        session.createMutationQuery("""
                DELETE Post p WHERE p.author.id = :id
                """)
                .setParameter("id", id)
                .executeUpdate();
        //удаление самого пользователя
        session.createMutationQuery("""
                DELETE User u WHERE u.id = :id
                """)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void delete(User entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        for (long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<User> entities) {
        for (User user : entities) {
            deleteById(user.getId());
        }
    }
}
