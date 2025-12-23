package com.akiora.searchformservice.Domain.ValueObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChampionStats {
    private String position;
    private String champion;
    private String wins;
    private String losses;
    private String winRate;
    private String kdaRatio;
    private String kda;
    private String laning;
    private String damagePerMinute;
    private String damageShareRatio;
    private String wardsScore;
    private String wardsControl;
    private String cs;
    private String csPerMinute;
    private String gold;
    private String goldPerMinute;
    private String doubleKills;
    private String tripleKills;
    private String quadraKills;
    private String pentaKills;
}
