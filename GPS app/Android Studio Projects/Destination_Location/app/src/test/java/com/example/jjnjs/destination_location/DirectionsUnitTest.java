package com.example.jjnjs.destination_location;

import android.content.Context;

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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

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
@RunWith(RobolectricTestRunner.class)
public class DirectionsUnitTest {


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
    //test to see if correct duration and distance is returned
    public void getQuickestPerson1() throws IOException, JSONException, URISyntaxException {

        JSONObject j = new JSONObject(readJsonFile("sampledata/directions1.json"));
        Directions dist = new Directions(j);
        ArrayList<String> duration = dist.getDurationValue();
        ArrayList<String> distancevalue = dist.getDistanceValue();
        int totalduration = 0;
        int totaldistance = 0;
        for(int i = 0; i<duration.size(); i++){
            totalduration = totalduration + Integer.valueOf(duration.get(i));
            totaldistance = totaldistance + Integer.valueOf(distancevalue.get(i));
        }
        assertEquals(476,totalduration);
        assertEquals(3600,totaldistance);
    }

    @Test
    //test if origina and destination are same
    public void getQuickestPerson2() throws IOException, JSONException, URISyntaxException {

        JSONObject j = new JSONObject(readJsonFile("sampledata/directions2.json"));
        Directions dist = new Directions(j);
        ArrayList<String> durationvalue = dist.getDurationValue();
        ArrayList<String> distancevalue = dist.getDistanceValue();

        int totaldistance = 0;
        int totalduration = 0;
        for(int i = 0; i<durationvalue.size(); i++){
            totalduration = totalduration + Integer.valueOf(durationvalue.get(i));
            totaldistance = totaldistance + Integer.valueOf(distancevalue.get(i));
        }
        assertEquals(0,totalduration);
        assertEquals(0,totaldistance);
    }

    @Test
    //test if origina and destination are same
    public void getQuickestPerson3() throws IOException, JSONException, URISyntaxException {

        JSONObject j = new JSONObject(readJsonFile("sampledata/directions3.json"));
        Directions dist = new Directions(j);
        ArrayList<String> durationvalue = dist.getDurationValue();
        ArrayList<String> distancevalue = dist.getDistanceValue();

        int totaldistance = 0;
        int totalduration = 0;
        for(int i = 0; i<durationvalue.size(); i++){
            totalduration = totalduration + Integer.valueOf(durationvalue.get(i));
            totaldistance = totaldistance + Integer.valueOf(distancevalue.get(i));
        }
        assertEquals(-1,totalduration);
        assertEquals(-1,totaldistance);
    }
}