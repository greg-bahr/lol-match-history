package com.example.lolmatchhistory.api;

import com.example.lolmatchhistory.api.match.DetailedMatch;
import com.example.lolmatchhistory.api.match.MatchHistory;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public class RiotApi {
    private static RiotApi instance;

    private final HttpClient httpClient;
    private final String API_KEY = "";

    private RiotApi() {
        httpClient = HttpClient.newHttpClient();
    }

    public static RiotApi getInstance() {
        if (instance == null) {
            instance = new RiotApi();
        }
        return instance;
    }

    public User getUserByUsername(String username) throws IOException, InterruptedException {
        var endpoint = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s?api_key=%s";
        var request = HttpRequest.newBuilder(URI.create(String.format(endpoint, encodeStringURL(username), API_KEY)))
                .header("accept", "application/json")
                .build();
        var response = httpClient.send(request, new JsonBodyHandler<>(User.class));

        var user = response.body().get();
        user.setRank(getUserRankBySummonerId(user.getSummonerId()));
        user.setRecentMatches(getLastMatchesByAccountId(user.getAccountId(), 10).getMatches());

        return user;
    }

    public UserRank getUserRankBySummonerId(String summonerId) throws IOException, InterruptedException {
        //FIXME the returned response is an array
        var endpoint = "https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/%s?api_key=%s";
        var request = HttpRequest.newBuilder(URI.create(String.format(endpoint, summonerId, API_KEY)))
                .header("accept", "application/json")
                .build();
        var response = httpClient.send(request, new JsonBodyHandler<>(UserRank.class));

        return response.body().get();
    }

    public MatchHistory getLastMatchesByAccountId(String accountId, int matchCount) throws IOException, InterruptedException {
        var endpoint = "https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/%s?endIndex=%d&api_key=%s";
        var request = HttpRequest.newBuilder(URI.create(String.format(endpoint, accountId, matchCount - 1, API_KEY)))
                .header("accept", "application/json")
                .build();
        var response = httpClient.send(request, new JsonBodyHandler<>(MatchHistory.class));

        return response.body().get();
    }

    public DetailedMatch getDetailedMatchByMatchId(long matchId) throws IOException, InterruptedException {
        var endpoint = "https://na1.api.riotgames.com/lol/match/v4/matches/%d?api_key=%s";
        var request = HttpRequest.newBuilder(URI.create(String.format(endpoint, matchId, API_KEY)))
                .header("accept", "application/json")
                .build();
        var response = httpClient.send(request, new JsonBodyHandler<>(DetailedMatch.class));

        return response.body().get();
    }

    private String encodeStringURL(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
