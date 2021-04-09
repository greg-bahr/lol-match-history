package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchParticipant {
    private int participantId;
    private int championId;
    private MatchParticipantStats stats;
    private int teamId;
    private String highestAchievedSeasonTier;

    public MatchParticipant(
            @JsonProperty("participantId") int participantId,
            @JsonProperty("championId") int championId,
            @JsonProperty("stats") MatchParticipantStats stats,
            @JsonProperty("teamId") int teamId,
            @JsonProperty("highestAchievedSeasonTier") String highestAchievedSeasonTier
    ) {
        this.participantId = participantId;
        this.championId = championId;
        this.stats = stats;
        this.teamId = teamId;
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
    }

    public int getParticipantId() {
        return participantId;
    }

    public int getChampionId() {
        return championId;
    }

    public MatchParticipantStats getStats() {
        return stats;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }
}
