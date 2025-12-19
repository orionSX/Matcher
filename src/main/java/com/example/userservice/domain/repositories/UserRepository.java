package com.example.userservice.domain.repositories;

import com.example.userservice.domain.models.BaseUser;
import com.example.userservice.domain.models.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<BaseUser, UUID> {
    List<BaseUser> findByType(UserType type);
    Optional<BaseUser> findByOid(UUID oid);
}
