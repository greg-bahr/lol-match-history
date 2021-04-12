package com.example.lolmatchhistory;

import com.example.lolmatchhistory.api.RiotApi;
import com.example.lolmatchhistory.api.match.DetailedMatch;
import com.example.lolmatchhistory.api.match.Match;
import com.example.lolmatchhistory.api.match.MatchParticipantStats;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Menu {
    private User user;
    private RiotApi api;

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
            System.out.println("Profile for " + user.getName());
            System.out.println("1. Show player details");
            System.out.println("2. Show match history");
            System.out.println("3. View a match");
            System.out.println("0. Go back");
            printDashLine();
            int userInput = Integer.parseInt(inputOutput("Please press an appropriate number option."));
            if (userInput >= 0 && userInput <=3) {
                if (userInput == 1) showPlayerDetails();
                if (userInput == 2) showMatchHistory();
                if (userInput == 3) viewOneMatch();
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

    private void viewOneMatch() {
        //TODO print 1 match details
        viewProfileOptions();
    }

    private void showMatchHistory() {
        System.out.format("%16s %8s %15s %8s %10s%n",
                "Date","Status","Champion","K/D/A", "Duration");
        for (Match match : user.getRecentMatches()){
            DetailedMatch detailedMatch = match.getDetailedMatch();

            MatchParticipantStats playerStat = match.getParticipant().getStats();
            String gameStatus = (playerStat.isWin()) ? "Won" : "Lost";

            System.out.format("%16s %8s %15s %8s %10s%n",
                    getDateFromTimestamp(match.getTimestamp()), gameStatus,
                    api.getChampions().get(String.valueOf(match.getChampion())), playerStat.getKDA(),
                    getMinuteFromSecond(detailedMatch.getGameDuration()));
        }
        viewProfileOptions();
    }

    private void showPlayerDetails() {
        UserRank rank = user.getRankedInfo();
        System.out.format("Name: %s%n", user.getName());
        System.out.format("Level: %d%n", user.getLevel());
        if (Objects.isNull(rank)){
            System.out.println("No Rank information found.");
        }
        else{
            System.out.format("%d Wins\t%d Losses %n", rank.getWins(), rank.getLosses());
            System.out.format("Rank: %s %s %d LP%n", rank.getTier(), rank.getRank(), rank.getLeaguePoints());
        }
        viewProfileOptions();
    }

    private String getMinuteFromSecond(long time) {
        long seconds = time%60;
        long minutes = time/60;
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
