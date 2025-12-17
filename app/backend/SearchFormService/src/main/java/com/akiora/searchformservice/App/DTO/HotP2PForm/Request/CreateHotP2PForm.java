package com.akiora.searchformservice.App.DTO.HotP2PForm.Request;


import com.akiora.searchformservice.Domain.ValueObjects.LeaguePreferences;
import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;
import com.akiora.searchformservice.Domain.ValueObjects.UserData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotP2PForm {
    
    @Valid
    @Schema(name = "user_data")
    private UserData userData;
    
    @NotBlank   
    @Schema(name = "creator_id",requiredMode = Schema.RequiredMode.REQUIRED)
    private String creatorId;
    
    @NotBlank
    @Size(min = 5 , max = 500 )    
    private String description;
    
    @Valid
    @NotNull
    @Schema(name="league_preferences")
    private LeaguePreferences leaguePreferences;

    @Valid
    @Schema(name="person_preferences")
    private PersonPreferences personPreferences;
    
    
}
