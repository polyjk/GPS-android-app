package com.example.jjnjs.destination_location;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class DistanceMatrixUnitTest {
    public String readJsonFile (String file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }

        return sb.toString();
    }


    @Test
    //test 2 people in one order
    public void getQuickestPerson1() throws IOException, JSONException, URISyntaxException {

        ArrayList<LatLng> origins = new ArrayList<>();
        LatLng person1 = new LatLng(30.224946,-92.019170);
        LatLng person2 = new LatLng(30.214088258642757,-92.05347070907365);
        origins.add(person1);
        origins.add(person2);
        JSONObject j = new JSONObject(readJsonFile("sampledata/distancematrix1.json"));

        DistanceMatrix dist = new DistanceMatrix(j, origins);
        assertEquals(person2,dist.getQuickest());
    }

    @Test
    //test same 2 people in reverse order
    public void getQuickestPerson2() throws IOException, JSONException, URISyntaxException {

        ArrayList<LatLng> origins = new ArrayList<>();
        LatLng person1 = new LatLng(30.224946,-92.019170);
        LatLng person2 = new LatLng(30.214088258642757,-92.05347070907365);
        origins.add(person1);
        origins.add(person2);
        JSONObject j = new JSONObject(readJsonFile("sampledata/distancematrix2.json"));

        DistanceMatrix dist = new DistanceMatrix(j, origins);
        assertEquals(person1,dist.getQuickest());
    }

    @Test
    //test where there is one person
    public void getQuickestPerson3() throws IOException, JSONException, URISyntaxException {

        ArrayList<LatLng> origins = new ArrayList<>();
        LatLng person1 = new LatLng(30.224946,-92.019170);
        origins.add(person1);
        JSONObject j = new JSONObject(readJsonFile("sampledata/distancematrix3.json"));

        DistanceMatrix dist = new DistanceMatrix(j, origins);
        assertEquals(person1,dist.getQuickest());
    }

    @Test
    //test if theres an invalid request, the 1000,1000 latlng will be treated as an invalid and will execute a response to user that there was a problem.
    public void getQuickestPerson4() throws IOException, JSONException, URISyntaxException {

        ArrayList<LatLng> origins = new ArrayList<>();
        JSONObject j = new JSONObject(readJsonFile("sampledata/distancematrix4.json"));

        DistanceMatrix dist = new DistanceMatrix(j, origins);
        LatLng loc = new LatLng(1000,1000);
        assertEquals(loc,dist.getQuickest());
    }

    @Test
    //test case if there are two people who share the location
    public void getQuickestPerson5() throws IOException, JSONException, URISyntaxException {

        ArrayList<LatLng> origins = new ArrayList<>();
        LatLng person1 = new LatLng(30.224946,-92.019170);
        LatLng person2 = new LatLng(30.224946,-92.019170);
        origins.add(person1);
        origins.add(person2);
        JSONObject j = new JSONObject(readJsonFile("sampledata/distancematrix5.json"));

        DistanceMatrix dist = new DistanceMatrix(j, origins);

        assertEquals(person1,dist.getQuickest());
        assertEquals(0, dist.indexQuickest);
    }

    @Test
    //test case with several people's locations
    public void getQuickestPerson6() throws IOException, JSONException, URISyntaxException {

        ArrayList<LatLng> origins = new ArrayList<>();
        LatLng person1 = new LatLng(30.224946,-92.019170);
        LatLng person2 = new LatLng(30.205518,-92.044768);
        LatLng person3 = new LatLng(30.206176,-92.034136);
        LatLng person4 = new LatLng(30.210923,-92.028611);
        LatLng person5 = new LatLng(30.213899,-92.016755);
        origins.add(person1);
        origins.add(person2);
        origins.add(person3);
        origins.add(person4);
        origins.add(person5);
        JSONObject j = new JSONObject(readJsonFile("sampledata/distancematrix6.json"));

        DistanceMatrix dist = new DistanceMatrix(j, origins);

        assertEquals(person2,dist.getQuickest());
    }
}