package com.akiora.searchformservice.App.DTO.HotP2PForm.Request;

import com.akiora.searchformservice.Domain.ValueObjects.LeagueAccount;
import com.akiora.searchformservice.Domain.ValueObjects.LeaguePreferences;
import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHotP2PForm {
    
    @Valid
    @Schema(name = "account")
    private LeagueAccount account;
    
    @Size(min = 5, max = 500)
    @Schema(name = "description")
    private String description;
    
    @Valid
    @Schema(name = "league_preferences")
    private LeaguePreferences leaguePreferences;

    @Valid
    @Schema(name = "person_preferences")
    private PersonPreferences personPreferences;
}
