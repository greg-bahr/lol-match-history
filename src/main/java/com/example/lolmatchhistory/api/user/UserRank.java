package com.example.lolmatchhistory.api.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRank {
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    public UserRank(
            @JsonProperty("leagueId") String leagueId,
            @JsonProperty("queueType") String queueType,
            @JsonProperty("tier") String tier,
            @JsonProperty("rank") String rank,
            @JsonProperty("leaguePoints") int leaguePoints,
            @JsonProperty("wins") int wins,
            @JsonProperty("losses") int losses
    ) {
        this.leagueId = leagueId;
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getQueueType() {
        return queueType;
    }

    public String getTier() {
        return tier;
    }

    public String getRank() {
        return rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
