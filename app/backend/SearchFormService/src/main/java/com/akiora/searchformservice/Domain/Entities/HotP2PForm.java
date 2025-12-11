package com.akiora.searchformservice.Domain.Entities;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Domain.ValueObjects.GamePreferences;
import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.support.InstantiationStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import com.fasterxml.uuid.Generators;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.time.Instant;



@Document(collection = "hot_p2p_forms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotP2PForm {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    @Field(targetType = FieldType.STRING)
    private UUID creatorId;
    private GamePreferences gamePreferences;
    private PersonPreferences personPreferences;   
    private String description;
    private Instant createdAt;
    
    private Instant expiresAt;
    
    public static HotP2PForm Create(
            UUID creatorId,  GamePreferences gamePreferences, PersonPreferences personPreferences, String description
    ) throws DomainException {
        
        UUID newId = Generators.timeBasedEpochGenerator().generate();
        if (!GamePreferences.isValid(gamePreferences) || !PersonPreferences.isValid(personPreferences)) {
            throw new DomainException("invalid input");
        }
        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);
        return new HotP2PForm(newId,creatorId,gamePreferences,personPreferences,description,now,exp);
    }
   
}
