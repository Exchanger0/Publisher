package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends ListCrudRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
