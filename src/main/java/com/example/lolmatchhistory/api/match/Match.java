package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Not a lot of match data, for more detailed match data use DetailedMatch
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    private long matchId;
    private String role;
    private String lane;
    private int champion;
    private long timestamp;

    public Match(
            @JsonProperty("gameId") long matchId,
            @JsonProperty("role") String role,
            @JsonProperty("lane") String lane,
            @JsonProperty("champion") int champion,
            @JsonProperty("timestamp") long timestamp
    ) {
        this.matchId = matchId;
        this.role = role;
        this.lane = lane;
        this.champion = champion;
        this.timestamp = timestamp;
    }

    public long getMatchId() {
        return matchId;
    }

    public String getRole() {
        return role;
    }

    public String getLane() {
        return lane;
    }

    public int getChampion() {
        return champion;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
