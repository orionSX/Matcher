package com.akiora.searchformservice.Domain.ValueObjects;

import com.akiora.searchformservice.Domain.Enums.Rank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class LeagueRank{
    private Rank rank;
    
    @Max(4)
    @Min(1)
    private Integer tier=1;
   
}