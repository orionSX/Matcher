package com.akiora.searchformservice.App.DTO.HotP2PForm.Response;

import com.akiora.searchformservice.Domain.ValueObjects.LeaguePreferences;
import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;
import com.akiora.searchformservice.Domain.ValueObjects.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetHotP2PForm {
    
    private UUID id;    
    private UUID creatorId;
    private LeaguePreferences leaguePreferences;
    private PersonPreferences personPreferences;
    private UserData  userData;
    private String description;
    private Instant createdAt;
}
