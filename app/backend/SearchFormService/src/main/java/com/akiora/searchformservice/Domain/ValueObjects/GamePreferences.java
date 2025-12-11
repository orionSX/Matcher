package com.akiora.searchformservice.Domain.ValueObjects;

import com.akiora.searchformservice.Domain.Enums.GameMode;
import com.akiora.searchformservice.Domain.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GamePreferences{
    private List<GameMode> mode;
    private List<LeagueRank>lookingForRanks;
    private List<Role> myRoles;
    private List<Role> teammateRoles;
    private Boolean smurfOnly;
    private Boolean isRanked(){
        return mode!=null && (mode.contains(GameMode.FLEX) || mode.contains(GameMode.SOLOQ));
    }
    
    public static boolean isValid(GamePreferences gp){
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


