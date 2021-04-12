package com.example.lolmatchhistory.api.user;

import com.example.lolmatchhistory.api.match.Match;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String accountId;
    private String summonerId;
    private int level;
    private UserRank rankedInfo;
    private Match[] recentMatches;

    public User(
            @JsonProperty("name") String name,
            @JsonProperty("accountId") String accountId,
            @JsonProperty("id") String summonerId,
            @JsonProperty("summonerLevel") int level
    ) {
        this.name = name;
        this.accountId = accountId;
        this.level = level;
        this.summonerId = summonerId;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public int getLevel() {
        return level;
    }

    public UserRank getRankedInfo() {
        return rankedInfo;
    }

    public void setRankedInfo(UserRank rankedInfo) {
        this.rankedInfo = rankedInfo;
    }

    public Match[] getRecentMatches() {
        return recentMatches;
    }

    public void setRecentMatches(Match[] recentMatches) {
        this.recentMatches = recentMatches;
    }
}
