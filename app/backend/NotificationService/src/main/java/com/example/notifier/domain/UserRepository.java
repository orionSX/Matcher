package com.example.notifier.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    User save(User user);

}