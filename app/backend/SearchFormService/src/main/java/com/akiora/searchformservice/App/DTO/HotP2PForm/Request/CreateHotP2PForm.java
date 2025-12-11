package com.akiora.searchformservice.App.DTO.HotP2PForm.Request;


import com.akiora.searchformservice.Domain.Enums.GameMode;
import com.akiora.searchformservice.Domain.Enums.Gender;
import com.akiora.searchformservice.Domain.Enums.Role;
import com.akiora.searchformservice.Domain.ValueObjects.LeagueRank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class CreateHotP2PForm {
    
    @NotBlank
    @Schema(name = "creator_id",requiredMode = Schema.RequiredMode.REQUIRED)
    private String creatorId;
    
    @NotBlank
    @Size(min =5 , max = 500 )    
    private String description;
    
    //GamePrefs
    @NotEmpty
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private List<GameMode> mode;
    
    @Schema(name = "looking_for_ranks")
    private List<LeagueRank> lookingForRanks;
    
    @NotEmpty
    @Schema(name = "my_roles")
    private List<Role> myRoles;
    
    @NotEmpty
    @Schema(name = "teammate_roles")
    private List<Role> teammateRoles;
    
    @Schema(name = "smurf_only")
    private Boolean smurfOnly;
    
    //PersonPrefs
    @Min(12)
    @Max(120)
    @Schema(name = "min_age")
    private Integer minAge;
    
    @Min(12)
    @Max(120)
    @Schema(name = "max_age")
    private Integer maxAge;
    private Gender gender;
    private Boolean voice;
    
}
