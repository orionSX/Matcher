package com.example.notifier.domain.adapter;

import com.example.notifier.domain.User;
import com.example.notifier.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MongoUserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public MongoUserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public Optional<User> findById(String id) {
        return springDataUserRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return springDataUserRepository.save(user);
    }
}