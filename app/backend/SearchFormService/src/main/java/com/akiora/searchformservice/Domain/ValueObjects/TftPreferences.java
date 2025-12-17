package com.akiora.searchformservice.Domain.ValueObjects;

import com.akiora.searchformservice.Domain.Enums.GameMode;
import com.akiora.searchformservice.Domain.Enums.Role;
import com.akiora.searchformservice.Domain.Enums.Server;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// TODO tft logic
@Data
@AllArgsConstructor
public class TftPreferences {
    @NotEmpty
    private List<GameMode> mode;
    @Schema(name = "looking_for_ranks")
    private List<LeagueRank>lookingForRanks; //TODO remove
    @NotEmpty
    @Schema(name = "my_roles")
    private List<Role> myRoles;
    @NotEmpty
    @Schema(name = "teammate_roles")
    private List<Role> teammateRoles;
    @Schema(name = "smurf_only")
    private Boolean smurfOnly;
    @NotEmpty
    private List<Server> server;
    
}


