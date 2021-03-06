package com.example.lolmatchhistory.api;

import com.example.lolmatchhistory.api.match.DetailedMatch;
import com.example.lolmatchhistory.api.match.MatchHistory;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

import static com.example.lolmatchhistory.api.JsonReader.readJsonFromUrl;

public class RiotApi {
    private static RiotApi instance;

    private final HttpClient httpClient;
    private final String latestPatch;

    private HashMap<String, String> champions;

    private final String API_KEY;
    private RiotApi() {
        httpClient = HttpClient.newHttpClient();
        API_KEY = getKeyFromFile("api_key.txt");
        latestPatch = getPatch();
        champions = generateChampionsMap();
    }

    public HashMap<String, String> generateChampionsMap(){
        HashMap<String, String> champions = new HashMap<>();
        JSONObject json = null;
        try {
            String url = String.format("http://ddragon.leagueoflegends.com/cdn/%s/data/en_US/champion.json", latestPatch);
            json = readJsonFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        json = (JSONObject) json.get("data");
        Iterator<String> keys = json.keys();
        while(keys.hasNext()) {
            String championName = keys.next();
            JSONObject championDetails = (JSONObject) json.get(championName);
            champions.put((String) championDetails.get("key"), championName);
        }

        return champions;
    }

    private String getPatch(){
        String patch="";
        try {
            patch = (String) readJsonFromUrl("https://ddragon.leagueoflegends.com/realms/na.json").get("v");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patch;
    }

    private String getKeyFromFile(String fileName) {
        String api_key = "";
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                api_key = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read api key file");
            e.printStackTrace();
        }
        return api_key;
    }

    public static RiotApi getInstance(){
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
        if (!Objects.isNull(user.getName())){
            user.setRankedInfo(getUserRankBySummonerId(user.getSummonerId()));
            user.setRecentMatches(getLastMatchesByAccountId(user.getAccountId(), 11).getMatches());
        }
        return user;
    }

    public UserRank getUserRankBySummonerId(String summonerId) throws IOException, InterruptedException {
        var endpoint = "https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/%s?api_key=%s";
        var request = HttpRequest.newBuilder(URI.create(String.format(endpoint, summonerId, API_KEY)))
                .header("accept", "application/json")
                .build();
        var response = httpClient.send(request, new JsonBodyHandler<>(UserRank[].class));
        UserRank rankInfo = null;
        for (UserRank league : response.body().get()) {
            if (league.getQueueType().equals("RANKED_SOLO_5x5")) {
                rankInfo = league;
                break;
            }
        }

        return rankInfo;
    }

    public MatchHistory getLastMatchesByAccountId(String accountId, int endIndex) throws IOException, InterruptedException {
        var endpoint = "https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/%s?endIndex=%d&queue=420&api_key=%s";
        var request = HttpRequest.newBuilder(URI.create(String.format(endpoint, accountId, endIndex - 1, API_KEY)))
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

    public String getLatestPatch() { return latestPatch; }

    public String getChampionFromId(int id) {
        return champions.get(String.valueOf(id));
    }
}
