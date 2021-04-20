package com.example.lolmatchhistory;

import com.example.lolmatchhistory.api.RiotApi;
import com.example.lolmatchhistory.api.match.DetailedMatch;
import com.example.lolmatchhistory.api.match.MatchHistory;
import com.example.lolmatchhistory.api.user.User;
import com.example.lolmatchhistory.api.user.UserRank;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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
        assertEquals("""
                Profile for grefory
                1. Show player details
                2. Show match history
                3. View a match
                0. Go back
                """, menu.getProfileOptionsText("grefory"));
    }

    @Test
    public void printsPlayerDetails() {
        menu.user = new User("testuser", "", "", 36);

        assertEquals("""
                Name: testuser
                Level: 36""", menu.getPlayerDetailsText("testuser", 36));
    }

    @Test
    public void printsPlayerRankedDetails() {
        UserRank rank = new UserRank("", "", "PLATINUM", "III", 30, 100, 50);

        assertEquals("""
                100 Wins\t50 Losses
                Rank: PLATINUM III 30 LP""", menu.getPlayerRankText(rank));
    }

    @Test
    public void printsMatchHistoryLine() {
        assertEquals("2021-04-16 16:52     Lost            Udyr    6/5/7    30m 31s\r\n",
                menu.getMatchHistoryLine("2021-04-16 16:52", "Lost", "Udyr", "6/5/7", "30m 31s"));
    }

    @Test
    public void printsMatchParticipantDetails() throws IOException, InterruptedException {
        assertEquals("                    Tristana  16/6/17    16141     196992   158    24\r\n",
                menu.getMatchParticipantText("Tristana", "16/6/17", 16141, 196992, 158, 24));
    }

    @Test
    public void getsChampionName() {
        String champion = api.getChampionFromId(266);

        assertEquals("Aatrox", champion);
    }

    @Test
    public void getsSummonerRank() throws IOException, InterruptedException {
        User user = api.getUserByUsername("grefory");

        assertNotNull(user.getRankedInfo());
    }

    @Test
    public void getsTenMatches() throws IOException, InterruptedException {
        User user = api.getUserByUsername("grefory");
        MatchHistory matchHistory = api.getLastMatchesByAccountId(user.getAccountId(), 11);

        assertEquals(10, matchHistory.getMatches().length);
    }

    @Test
    public void getsNoMatchesForFakeId() throws IOException, InterruptedException {
        MatchHistory matchHistory = api.getLastMatchesByAccountId("123", 11);

        assertNull(matchHistory.getMatches());
    }

    @Test
    public void getsDetailedMatchInformation() throws IOException, InterruptedException {
        User user = api.getUserByUsername("grefory");
        MatchHistory matchHistory = api.getLastMatchesByAccountId(user.getAccountId(), 11);
        DetailedMatch detailedMatch = api.getDetailedMatchByMatchId(matchHistory.getMatches()[0].getMatchId());

        assertNotNull(detailedMatch);
    }
}
