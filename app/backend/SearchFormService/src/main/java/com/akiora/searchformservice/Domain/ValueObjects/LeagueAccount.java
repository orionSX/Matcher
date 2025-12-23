package com.akiora.searchformservice.Domain.ValueObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueAccount {
    private String nickname;
    private String server;
    private String tag;
}
