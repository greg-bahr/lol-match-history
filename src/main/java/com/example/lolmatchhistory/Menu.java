package com.example.lolmatchhistory;

import com.example.lolmatchhistory.api.RiotApi;
import com.example.lolmatchhistory.api.match.*;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Menu {
    User user;
    private final RiotApi api;

    public Menu() {
        api = RiotApi.getInstance();
    }
    public void mainMenu(){
        System.out.println("1. Find Player");
        System.out.println("0. Exit\n");

        try {
            int userInput = Integer.parseInt(inputOutput("Please press an appropriate number option: "));
            if (userInput >= 0 && userInput <=1) {
                if (userInput == 1) findPlayer();
                if (userInput == 0) System.exit(0);
            } else {
                System.out.println("Please enter a number from 0 - 1");
                mainMenu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number from 0 - 1");
            mainMenu();
        }
    }

    private void findPlayer() {
        String name = inputOutput("Please enter a name: ");
        try {
            user = RiotApi.getInstance().getUserByUsername(name);
            if (Objects.isNull(user.getName())){
                System.out.println("This name does not exists. Please try again.");
                mainMenu();
            }
            else{
                viewProfileOptions();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void viewProfileOptions() {
        try{
            printDashLine();
            System.out.print(getProfileOptionsText(user.getName()));
            printDashLine();
            int userInput = Integer.parseInt(inputOutput("Please press an appropriate number option."));
            if (userInput >= 0 && userInput <=3) {
                if (userInput == 1) showPlayerDetails();
                if (userInput == 2) showMatchHistory();
                if (userInput == 3) showOneMatchMenu();
                if (userInput == 0) mainMenu();
            } else {
                System.out.println("Please enter a number from 0 - 3");
                viewProfileOptions();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number from 0 - 3");
            viewProfileOptions();
        }
    }

    public String getProfileOptionsText(String userName) {
        return String.format("""
                Profile for %s
                1. Show player details
                2. Show match history
                3. View a match
                0. Go back
                """, userName);
    }

    private void showOneMatchMenu() {
        int numRecentMatch = user.getRecentMatches().length;
        try {
            System.out.format("1 - %s. Choose a match %n", numRecentMatch);
            System.out.println("0. Go back");
            int userInput = Integer.parseInt(inputOutput("Please press an appropriate number option."));
            if (userInput >= 0 && userInput <= numRecentMatch) {
                if (userInput == 0)
                    viewProfileOptions();
                else{
                    viewMatch(user.getRecentMatches()[userInput - 1]);
                }
            } else {
                System.out.format("Please enter a number from 0 - %s%n", numRecentMatch);
                showOneMatchMenu();
            }
        } catch (NumberFormatException e) {
            System.out.format("Please enter a number from 0 - %s%n", numRecentMatch);
            showOneMatchMenu();
        }
    }

    private void viewMatch(Match match) {
        DetailedMatch detailedMatch = match.getDetailedMatch();
        String matchDate = getDateFromTimestamp(match.getTimestamp());
        String duration = getMinuteFromSecond(detailedMatch.getGameDuration());
        int playerChampionId = match.getChampion();
        System.out.format("Match date: %s%n", matchDate);
        System.out.format("Match duration: %s%n", duration);

        System.out.format("%12s %15s %8s %8s %10s %5s %5s%n",
                "", "Champion", "K/D/A", "Gold", "Damage", "CS", "VisionScore");
        HashMap<Integer, String> teamStatus = new HashMap<>();
        for (TeamStats team : detailedMatch.getTeams()){
            if (team.getWin().equals("Win")){
                teamStatus.put(team.getTeamId(), "Won");
            }
            else{
                teamStatus.put(team.getTeamId(), "Lost");
            }
        }
        System.out.format("Blue (%s)%n", teamStatus.get(100));
        printParticipantsDetails(detailedMatch.getBlueParticipants(), playerChampionId);
        System.out.format("Red (%s)%n", teamStatus.get(200));
        printParticipantsDetails(detailedMatch.getRedParticipants(),playerChampionId);

        printDashLine();
        showOneMatchMenu();
    }

    private void printParticipantsDetails(MatchParticipant[] participants, int playerChampionId) {
        for (MatchParticipant participant : participants){
            MatchParticipantStats stats = participant.getStats();

            String championName = api.getChampionFromId(participant.getChampionId());
            if (playerChampionId==participant.getChampionId()){
                championName += "(you)";
            }
            System.out.print(getMatchParticipantText(championName,
                    stats.getKDA(),
                    stats.getGoldEarned(),
                    stats.getTotalDamageDealt(),
                    stats.getTotalMinionsKilled(),
                    stats.getVisionScore()));
        }
    }

    public String getMatchParticipantText(String championName, String kda, int goldEarned, long totalDamageDealt, int minionsKilled, long visionScore) {
        return String.format("%12s %15s %8s %8s %10s %5s %5s%n", "",
                championName,
                kda,
                goldEarned,
                totalDamageDealt,
                minionsKilled,
                visionScore);
    }

    private void showMatchHistory() {
        String stringFormat = "%3s %16s %8s %15s %8s %10s%n";
        System.out.format(stringFormat, "", "Date", "Status", "Champion", "K/D/A", "Duration");
        int i = 1;
        for (Match match : user.getRecentMatches()) {
            DetailedMatch detailedMatch = match.getDetailedMatch();

            MatchParticipantStats playerStat = match.getPlayer().getStats();
            String gameStatus = (playerStat.isWin()) ? "Won" : "Lost";
            String line = getMatchHistoryLine(
                    getDateFromTimestamp(match.getTimestamp()),
                    gameStatus,
                    api.getChampionFromId(match.getChampion()),
                    playerStat.getKDA(),
                    getMinuteFromSecond(detailedMatch.getGameDuration()));

            System.out.format("%3s %s", i, line);
            i += 1;
        }
        viewProfileOptions();
    }

    public String getMatchHistoryLine(String date, String gameStatus, String champion, String kda, String minutes) {
        return String.format("%16s %8s %15s %8s %10s%n", date, gameStatus, champion, kda, minutes);
    }

    private void showPlayerDetails() {
        System.out.println(getPlayerDetailsText(user.getName(), user.getLevel()));
        System.out.println(getPlayerRankText(user.getRankedInfo()));
        viewProfileOptions();
    }

    public String getPlayerDetailsText(String name, int level) {
        return String.format("""
                Name: %s
                Level: %d""", name, level);
    }

    public String getPlayerRankText(UserRank rank) {
        if (rank == null) {
            return "No Rank information found.\n";
        }
        return String.format("""
                        %d Wins\t%d Losses
                        Rank: %s %s %d LP""",
                rank.getWins(), rank.getLosses(), rank.getTier(), rank.getRank(), rank.getLeaguePoints());
    }

    private String getMinuteFromSecond(long time) {
        long seconds = time % 60;
        long minutes = time / 60;
        return String.format("%sm %ss", minutes, seconds);
    }

    private String getDateFromTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return jdf.format(date);
    }

    private String inputOutput(String message) {
        System.out.println(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String returnString = "";
        try {
            returnString = br.readLine();
        }
        catch (IOException e){
            System.out.println("Error reading in value");
            mainMenu();
        }
        return returnString;
    }

    private void printDashLine() {
        System.out.println("--------------------------------------------------");
    }

}
