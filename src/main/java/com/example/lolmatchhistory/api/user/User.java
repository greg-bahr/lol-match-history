package com.example.lolmatchhistory.api.user;

import com.example.lolmatchhistory.api.match.Match;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String accountId;
    private int level;
    private UserRank rank;
    private Match[] recentMatches;

    public User(
            @JsonProperty("name") String name,
            @JsonProperty("accountId") String accountId,
            @JsonProperty("summonerLevel") int level
    ) {
        this.name = name;
        this.accountId = accountId;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getLevel() {
        return level;
    }

    public UserRank getRank() {
        return rank;
    }

    public void setRank(UserRank rank) {
        this.rank = rank;
    }

    public Match[] getRecentMatches() {
        return recentMatches;
    }

    public void setRecentMatches(Match[] recentMatches) {
        this.recentMatches = recentMatches;
    }
}
