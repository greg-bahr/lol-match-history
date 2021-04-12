package com.example.lolmatchhistory.api.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedMatch {
    private long matchId;
    private MatchParticipant[] participants;

    private MatchParticipant[] blueParticipants = new MatchParticipant[5];

    private MatchParticipant[] redParticipants = new MatchParticipant[5];
    private int queueId;

    private String gameType;
    private String gameVersion;

    private long gameDuration;

    private TeamStats[] teams;
    public DetailedMatch(
            @JsonProperty("gameId") long matchId,
            @JsonProperty("participants") MatchParticipant[] participants,
            @JsonProperty("queueId") int queueId,
            @JsonProperty("gameType") String gameType,
            @JsonProperty("gameDuration") long gameDuration,
            @JsonProperty("gameVersion") String gameVersion,
            @JsonProperty("teams") TeamStats[] teams
    ) {
        this.matchId = matchId;
        this.participants = participants;
        this.queueId = queueId;
        this.gameType = gameType;
        this.gameDuration = gameDuration;
        this.teams = teams;
        this.gameVersion = gameVersion;

        setParticipantTeam();
    }
    private void setParticipantTeam() {
        int blueCount = 0;
        int redCount = 0;
        for (MatchParticipant participant : participants){
            if (participant.getTeamId()==100){
                blueParticipants[blueCount]=participant;
                blueCount++;
            }
            else{
                redParticipants[redCount]=participant;
                redCount++;
            }
        }
    }

    public long getMatchId() {
        return matchId;
    }

    public int getQueueId() {
        return queueId;
    }

    public String getGameVersion() { return gameVersion; }

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

    public MatchParticipant[] getBlueParticipants() { return blueParticipants; }

    public MatchParticipant[] getRedParticipants() { return redParticipants; }

    public MatchParticipant getParticipantFromChampion(int championId) {
        for (MatchParticipant participant :participants){
            if (participant.getChampionId()==championId){
                return participant;
            }
        }
        return null;
    }
}
