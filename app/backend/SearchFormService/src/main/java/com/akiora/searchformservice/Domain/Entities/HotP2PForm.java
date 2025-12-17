package com.akiora.searchformservice.Domain.Entities;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Domain.ValueObjects.LeaguePreferences;
import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;

import com.akiora.searchformservice.Domain.ValueObjects.UserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private LeaguePreferences leaguePreferences;
    private UserData userData;
    private PersonPreferences personPreferences;   
    private String description;
    private Instant createdAt;    
    private Instant expiresAt;
    
    public static HotP2PForm Create(
            UUID creatorId, LeaguePreferences leaguePreferences, PersonPreferences personPreferences, String description, UserData userData
    ) throws DomainException {
        
        UUID newId = Generators.timeBasedEpochGenerator().generate();
        if (!LeaguePreferences.isValid(leaguePreferences) || !PersonPreferences.isValid(personPreferences)) {
            throw new DomainException("invalid input");
        }
        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);
        return new HotP2PForm(newId,creatorId, leaguePreferences,userData,personPreferences,description,now,exp);
    }
    public static HotP2PForm Create(
           CreateHotP2PForm createData
    ) throws DomainException {

        UUID newId = Generators.timeBasedEpochGenerator().generate();
        
        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);
        return new HotP2PForm(newId,
                UUID.fromString(createData.getCreatorId()),
                createData.getLeaguePreferences(),
                createData.getUserData(),
                createData.getPersonPreferences(),
                createData.getDescription(),
                now,
                exp);
    }
   
}
