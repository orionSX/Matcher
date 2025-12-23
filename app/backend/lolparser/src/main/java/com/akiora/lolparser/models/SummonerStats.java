package com.akiora.lolparser.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummonerStats {
    private RankedStats soloQueue = new RankedStats();
    private RankedStats flexQueue = new RankedStats();
    private List<ChampionStats> championStats = new ArrayList<>();
}
