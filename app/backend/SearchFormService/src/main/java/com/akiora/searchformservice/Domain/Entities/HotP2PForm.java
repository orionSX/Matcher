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
import java.util.List;
import java.util.ArrayList;



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
    @Builder.Default
    private List<String> likedBy = new ArrayList<>();
    @Builder.Default
    private List<String> dislikedBy = new ArrayList<>();
    
    public static HotP2PForm Create(
            UUID creatorId, LeaguePreferences leaguePreferences, PersonPreferences personPreferences, String description, UserData userData
    ) throws DomainException {
        
        UUID newId = Generators.timeBasedEpochGenerator().generate();
        if (!LeaguePreferences.isValid(leaguePreferences) || !PersonPreferences.isValid(personPreferences)) {
            throw new DomainException("invalid input");
        }
        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);
        return HotP2PForm.builder()
                .id(newId)
                .creatorId(creatorId)
                .leaguePreferences(leaguePreferences)
                .userData(userData)
                .personPreferences(personPreferences)
                .description(description)
                .createdAt(now)
                .expiresAt(exp)
                .likedBy(new ArrayList<>())
                .dislikedBy(new ArrayList<>())
                .build();
    }
    public static HotP2PForm Create(
           CreateHotP2PForm createData
    ) throws DomainException {

        UUID newId = Generators.timeBasedEpochGenerator().generate();
        
        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);
        return HotP2PForm.builder()
                .id(newId)
                .creatorId(UUID.fromString(createData.getCreatorId()))
                .leaguePreferences(createData.getLeaguePreferences())
                .userData(createData.getUserData())
                .personPreferences(createData.getPersonPreferences())
                .description(createData.getDescription())
                .createdAt(now)
                .expiresAt(exp)
                .likedBy(new ArrayList<>())
                .dislikedBy(new ArrayList<>())
                .build();
    }
   
}
