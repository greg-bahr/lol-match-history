package com.example.lolmatchhistory;

import com.example.lolmatchhistory.api.RiotApi;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Menu {
    private User user;

    public void mainMenu(){
        System.out.println("1. Find Player");
        System.out.println("0. Exit\n");

        try {
            int userInput = Integer.parseInt(inputOutput("Please press an appropriate number option."));
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
        //TODO print match history
        viewProfileOptions();
    }

    private void showPlayerDetails() {
        //TODO print player details
        UserRank rank = user.getRank();
        System.out.format("Name: %s%n", user.getName());
        System.out.format("Level: %d%n", user.getLevel());
        System.out.format("%d Wins\t%d Losses %n", rank.getWins(), rank.getLosses());
        System.out.println(rank.getLeagueId());
        viewProfileOptions();
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
