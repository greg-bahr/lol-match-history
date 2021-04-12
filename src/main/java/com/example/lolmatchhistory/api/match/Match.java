package com.example.lolmatchhistory.api.match;

import com.example.lolmatchhistory.api.RiotApi;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

// Not a lot of match data, for more detailed match data use DetailedMatch
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    private final long matchId;
    private final String role;
    private final String lane;
    private final int champion;
    private final long timestamp;

    private DetailedMatch detailedMatch;

    private MatchParticipant participant;

    public Match(
            @JsonProperty("gameId") long matchId,
            @JsonProperty("role") String role,
            @JsonProperty("lane") String lane,
            @JsonProperty("champion") int champion,
            @JsonProperty("timestamp") long timestamp
    ) throws IOException, InterruptedException {
        this.matchId = matchId;
        this.role = role;
        this.lane = lane;
        this.champion = champion;
        this.timestamp = timestamp;
        this.detailedMatch = RiotApi.getInstance().getDetailedMatchByMatchId(matchId);
        this.participant = this.detailedMatch.getParticipantFromChampion(champion);
    }

    public DetailedMatch getDetailedMatch() { return detailedMatch; }

    public MatchParticipant getParticipant() { return participant; }

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
