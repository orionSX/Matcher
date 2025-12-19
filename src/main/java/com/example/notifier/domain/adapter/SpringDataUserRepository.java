package com.example.notifier.domain.adapter;

import com.example.notifier.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}