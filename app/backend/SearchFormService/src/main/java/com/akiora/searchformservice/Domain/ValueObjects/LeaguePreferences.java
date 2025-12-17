package com.akiora.searchformservice.Domain.ValueObjects;

import com.akiora.searchformservice.Domain.Enums.GameMode;
import com.akiora.searchformservice.Domain.Enums.Role;
import com.akiora.searchformservice.Domain.Enums.Server;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LeaguePreferences {
    
    @NotEmpty
    private List<GameMode> mode;
    
    @Schema(name = "looking_for_ranks")
    private List<LeagueRank>lookingForRanks; //TODO remove cus it comes automatically
    
    @NotEmpty
    @Schema(name = "my_roles")
    private List<Role> myRoles;
    
    @NotEmpty
    @Schema(name = "teammate_roles")
    private List<Role> teammateRoles;
    
    @Schema(name = "smurf_only")
    @Builder.Default
    private Boolean smurfOnly=Boolean.FALSE;
    
    @NotEmpty
    private List<Server> server;
    
    private Boolean isRanked(){
        return mode!=null && (mode.contains(GameMode.FLEX) || mode.contains(GameMode.SOLOQ));
    }
    
    public static boolean isValid(LeaguePreferences gp){
        if(gp.mode==null || gp.mode.isEmpty()) {
            return false;
        }
        if(gp.isRanked()) {
            boolean fl = gp.lookingForRanks == null || gp.lookingForRanks.isEmpty();
            if(fl) return false;
        }
        
        return true;
    }
}


