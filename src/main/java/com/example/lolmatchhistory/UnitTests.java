package com.example.lolmatchhistory;

import com.example.lolmatchhistory.api.RiotApi;
import com.example.lolmatchhistory.api.user.User;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.lolmatchhistory.api.JsonReader.readJsonFromUrl;
import static org.junit.Assert.*;

public class UnitTests {
    RiotApi api;
    @Before
    public void setUp() {
        api = RiotApi.getInstance();
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
    public void checkGetPatchVersion(){
        String version = api.getLatestPatch();
        System.out.println(version);
    }

    @Test
    public void championIdNotExist(){
        assertNull(api.getChampionFromId(10000));
    }
}
