package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchHistory {
    private Match[] matches;

    public MatchHistory(
            @JsonProperty("matches") Match[] matches
    ) {
        this.matches = matches;
    }

    public Match[] getMatches() {
        return matches;
    }
}
