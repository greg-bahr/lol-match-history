package com.example.lolmatchhistory;

import com.example.lolmatchhistory.api.RiotApi;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UnitTests {
    RiotApi api;
    Menu menu;

    @Before
    public void setUp() {
        api = RiotApi.getInstance();
        menu = new Menu();
    }

    @Test
    public void getUserByName() throws IOException, InterruptedException {
        User user = api.getUserByUsername("nminhhh");
        assertEquals(String.class, user.getName().getClass());
    }

    @Test
    public void nameNotExists() throws IOException, InterruptedException {
        User user = api.getUserByUsername("Impossiblename1234");
        assertNull(user.getName());
    }

    @Test
    public void nameWithSpaces() throws IOException, InterruptedException {
        User user = api.getUserByUsername("DEMON SHACO");
        assertEquals(String.class, user.getName().getClass());
    }

    @Test
    public void checkGetPatchVersion() {
        String version = api.getLatestPatch();
        System.out.println(version);
    }

    @Test
    public void championIdNotExist() {
        assertNull(api.getChampionFromId(10000));
    }

    @Test
    public void printsMenuWhenUserNotNull() {
        assertEquals(menu.getProfileOptionsText("grefory"), """
                Profile for grefory
                1. Show player details
                2. Show match history
                3. View a match
                0. Go back
                """);
    }

    @Test
    public void printsPlayerDetails() {
        menu.user = new User("testuser", "", "", 36);

        assertEquals(menu.getPlayerDetailsText("testuser", 36), """
                Name: testuser
                Level: 36""");
    }

    @Test
    public void printsPlayerRankedDetails() {
        UserRank rank = new UserRank("", "", "PLATINUM", "III", 30, 100, 50);

        assertEquals(menu.getPlayerRankText(rank), """
                100 Wins\t50 Losses
                Rank: PLATINUM III 30 LP""");
    }

    @Test
    public void printsMatchHistoryLine() {
        assertEquals(
                menu.getMatchHistoryLine("2021-04-16 16:52", "Lost", "Udyr", "6/5/7", "30m 31s"),
                "2021-04-16 16:52     Lost            Udyr    6/5/7    30m 31s\r\n");
    }

    @Test
    public void printsMatchParticipantDetails() throws IOException, InterruptedException {
        assertEquals(menu.getMatchParticipantText("Tristana", "16/6/17", 16141, 196992, 158, 24),
                "                    Tristana  16/6/17    16141     196992   158    24\r\n");
    }
}
