package com.akiora.lolparser.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankedStats {
    private int iconId;
    private String currentRank;
    private String currentLP;
    private String winLoss;
    private String winRate;
    private String bestRank;
    private String bestLP;
}
