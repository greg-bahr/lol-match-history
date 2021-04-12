package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchParticipantStats {
    private int goldEarned;
    private int totalPlayerScore;
    private int champLevel;
    private long totalDamageTaken;
    private int deaths;
    private long totalDamageDealt;
    private int wardsPlaced;
    private int kills;
    private int assists;
    private boolean win;
    private boolean firstBloodKill;

    public MatchParticipantStats(
            @JsonProperty("goldEarned") int goldEarned,
            @JsonProperty("totalPlayerScore") int totalPlayerScore,
            @JsonProperty("champLevel") int champLevel,
            @JsonProperty("totalDamageTaken") long totalDamageTaken,
            @JsonProperty("deaths") int deaths,
            @JsonProperty("totalDamageDealt") long totalDamageDealt,
            @JsonProperty("wardsPlaced") int wardsPlaced,
            @JsonProperty("kills") int kills,
            @JsonProperty("assists") int assists,
            @JsonProperty("win") boolean win,
            @JsonProperty("firstBloodKill") boolean firstBloodKill
    ) {
        this.goldEarned = goldEarned;
        this.totalPlayerScore = totalPlayerScore;
        this.champLevel = champLevel;
        this.totalDamageTaken = totalDamageTaken;
        this.deaths = deaths;
        this.totalDamageDealt = totalDamageDealt;
        this.wardsPlaced = wardsPlaced;
        this.kills = kills;
        this.assists = assists;
        this.win = win;
        this.firstBloodKill = firstBloodKill;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public int getTotalPlayerScore() {
        return totalPlayerScore;
    }

    public int getChampLevel() {
        return champLevel;
    }

    public long getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public int getDeaths() {
        return deaths;
    }

    public long getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public int getWardsPlaced() {
        return wardsPlaced;
    }

    public int getKills() {
        return kills;
    }

    public int getAssists() {
        return assists;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isFirstBloodKill() {
        return firstBloodKill;
    }

    public String getKDA() {
        return String.format("%s/%s/%s", kills, deaths, assists);
    }
}
