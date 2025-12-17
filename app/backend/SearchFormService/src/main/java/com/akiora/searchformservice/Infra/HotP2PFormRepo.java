package com.akiora.searchformservice.Infra;

import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotP2PFormRepo extends MongoRepository<HotP2PForm,String> {

 
    Optional<HotP2PForm> findByCreatorId(String creatorId);


    List<HotP2PForm> findAllByCreatorId(UUID creatorId);


    List<HotP2PForm> findAllByCreatorId(String creatorId);
    List<HotP2PForm> findAllByCreatorIdOrderByCreatedAtAsc(String creatorId);
    List<HotP2PForm> findAllByCreatorIdOrderByCreatedAtDesc(String creatorId);
    @Query("{ 'creatorId': ?0 }")
    List<HotP2PForm> findFormsByCreatorId(String creatorId);
}
