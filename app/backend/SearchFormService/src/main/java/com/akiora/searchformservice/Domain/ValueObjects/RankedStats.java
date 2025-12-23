package com.akiora.searchformservice.Domain.ValueObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankedStats {
    private Integer iconId;
    private String currentRank;
    private String currentLP;
    private String winLoss;
    private String winRate;
    private String bestRank;
    private String bestLP;
}
