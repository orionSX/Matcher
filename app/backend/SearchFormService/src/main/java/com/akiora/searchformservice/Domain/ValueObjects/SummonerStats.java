package com.akiora.searchformservice.Domain.ValueObjects;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SummonerStats {
    private RankedStats soloQueue = new RankedStats();
    private RankedStats flexQueue = new RankedStats();
    private List<ChampionStats> championStats = new ArrayList<>();
}
