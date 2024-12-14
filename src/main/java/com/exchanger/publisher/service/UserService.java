package com.exchanger.publisher.service;

import com.exchanger.publisher.model.User;
import com.exchanger.publisher.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User, Long, UserRepo> {

    @Autowired
    public UserService(UserRepo repository) {
        super(repository);
    }

    public void save(String username, String password) {
        save(new User(username, password, "", List.of("USER_ROLE")));
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}
