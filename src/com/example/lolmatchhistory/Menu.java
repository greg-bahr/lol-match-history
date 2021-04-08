package com.example.lolmatchhistory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        user = new User(name);
        viewProfileOptions();
    }

    private void viewProfileOptions() {
        try{
            System.out.println("Profile for " + user.getName());
            System.out.println("1. Show player details");
            System.out.println("2. Show match history");
            System.out.println("3. View a match");
            System.out.println("0. Go back\n");
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
}
