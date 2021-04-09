package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamStats {
    private int towerKills;
    private int riftHeraldKills;
    private boolean firstBlood;
    private int dragonKills;
    private int baronKills;
    private String win;
    private int teamId;

    public TeamStats(
            @JsonProperty("towerKills") int towerKills,
            @JsonProperty("riftHeraldKills") int riftHeraldKills,
            @JsonProperty("firstBlood") boolean firstBlood,
            @JsonProperty("dragonKills") int dragonKills,
            @JsonProperty("baronKills") int baronKills,
            @JsonProperty("win") String win,
            @JsonProperty("teamId") int teamId
    ) {
        this.towerKills = towerKills;
        this.riftHeraldKills = riftHeraldKills;
        this.firstBlood = firstBlood;
        this.dragonKills = dragonKills;
        this.baronKills = baronKills;
        this.win = win;
        this.teamId = teamId;
    }

    public int getTowerKills() {
        return towerKills;
    }

    public int getRiftHeraldKills() {
        return riftHeraldKills;
    }

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public int getDragonKills() {
        return dragonKills;
    }

    public int getBaronKills() {
        return baronKills;
    }

    public String getWin() {
        return win;
    }

    public int getTeamId() {
        return teamId;
    }
}
