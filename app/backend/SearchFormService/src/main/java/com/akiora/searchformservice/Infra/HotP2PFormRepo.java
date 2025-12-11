package com.akiora.searchformservice.Infra;

import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotP2PFormRepo extends MongoRepository<HotP2PForm,String> {

    // findByCreatorId - поиск одного документа
    Optional<HotP2PForm> findByCreatorId(String creatorId);

    // findAllByCreatorId - поиск всех документов по creatorId
    List<HotP2PForm> findAllByCreatorId(UUID creatorId);

    // Или если creatorId хранится как String
    List<HotP2PForm> findAllByCreatorId(String creatorId);

    // Можно также использовать @Query для кастомных запросов
    @Query("{ 'creatorId': ?0 }")
    List<HotP2PForm> findFormsByCreatorId(String creatorId);
}
