package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedMatch {
    private long matchId;
    private MatchParticipant[] participants;
    private int queueId;
    private String gameType;
    private long gameDuration;
    private TeamStats[] teams;

    public DetailedMatch(
            @JsonProperty("gameId") long matchId,
            @JsonProperty("participants") MatchParticipant[] participants,
            @JsonProperty("queueId") int queueId,
            @JsonProperty("gameType") String gameType,
            @JsonProperty("gameDuration") long gameDuration,
            @JsonProperty("teams") TeamStats[] teams
    ) {
        this.matchId = matchId;
        this.participants = participants;
        this.queueId = queueId;
        this.gameType = gameType;
        this.gameDuration = gameDuration;
        this.teams = teams;
    }

    public long getMatchId() {
        return matchId;
    }

    public int getQueueId() {
        return queueId;
    }

    public String getGameType() {
        return gameType;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public TeamStats[] getTeams() {
        return teams;
    }

    public MatchParticipant[] getParticipants() {
        return participants;
    }
}
