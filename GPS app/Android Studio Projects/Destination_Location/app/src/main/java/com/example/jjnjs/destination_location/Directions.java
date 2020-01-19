package com.example.jjnjs.destination_location;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Directions {

    ArrayList<Bundle> list = new ArrayList<>();
    ArrayList<String> polylines = new ArrayList<>();
    ArrayList<String> distancetext = new ArrayList<>();
    ArrayList<String> durationtext = new ArrayList<>();
    ArrayList<String> distancevalue = new ArrayList<>();
    ArrayList<String> durationvalue = new ArrayList<>();
    Boolean valid = false;

    public Directions(JSONObject json) {
        //list = new ArrayList<>();


        try {
            String status = json.getString("status");
            if(status.matches("OK")){
                valid = true;
                JSONArray routes = json.getJSONArray("routes");
                JSONObject routesobj = routes.getJSONObject(0);
                JSONArray legs = routesobj.getJSONArray("legs");
                JSONObject legsobj = legs.getJSONObject(0);
                //Toast.makeText(Destination_Main.this,legs.toString(),Toast.LENGTH_LONG).show();
                JSONArray steps = legsobj.getJSONArray("steps");
                for(int i=0;i<steps.length();i++) {
                    JSONObject singleStep = steps.getJSONObject(i);
                    JSONObject duration = singleStep.getJSONObject("duration");
                    Bundle dur = new Bundle();
                    dur.putString("text", duration.getString("text"));
                    dur.putString("value", String.valueOf(duration.getInt("value")));

                    JSONObject distance = singleStep.getJSONObject("distance");
                    Bundle dis = new Bundle();
                    dis.putString("text", distance.getString("text"));
                    dis.putString("value", String.valueOf(distance.getInt("value")));
                    Bundle poly = new Bundle();
                    JSONObject polyline = singleStep.getJSONObject("polyline");

                    poly.putString("polyline", polyline.getString("points"));


                    Bundle instuct = new Bundle();
                    instuct.putString("instruction", singleStep.getString("html_instructions"));

                    Bundle data = new Bundle();


                    data.putBundle("duration", dur);
                    data.putBundle("distance", dis);
                    data.putBundle("polyline", poly);
                    list.add(data);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(valid == true){
            setPolylines();
            setDistanceString();
            setDistanceValue();
            setDurationString();
            setDurationValue();
        }else{
            setNoPoly();
            setNoDistanceString();
            setNoDistanceValue();
            setNoDurationString();
            setNoDurationValue();
        }
    }

    private void setPolylines() {
        for(int i = 0; i<list.size(); i++){
            Bundle poly = (Bundle) list.get(i).get("polyline");
            String polyline = poly.getString("polyline");
            Log.d("Points: ", polyline);
            polylines.add(polyline);
        }
    }

    private void setDistanceString() {
        for(int i = 0; i<list.size(); i++){
            Bundle dist = (Bundle) list.get(i).get("distance");
            String distan = dist.getString("text");
            distancetext.add(distan);
        }
    }

    private void setDistanceValue() {
        for(int i = 0; i<list.size(); i++){
            Bundle dist = (Bundle) list.get(i).get("distance");
            String distan = dist.getString("value");
            distancevalue.add(distan);
        }
    }

    private void setDurationString() {
        for(int i = 0; i<list.size(); i++){
            Bundle dur = (Bundle) list.get(i).get("duration");
            String durr = dur.getString("text");
            durationtext.add(durr);
        }
    }

    private void setDurationValue() {
        for(int i = 0; i<list.size(); i++){
            Bundle dur = (Bundle) list.get(i).get("duration");
            String durr = dur.getString("value");
            durationvalue.add(durr);
        }
    }

    public ArrayList<String> getPolylines() {
        return polylines;
    }

    public ArrayList<String> getDistanceValue() {
        return distancevalue;
    }

    public ArrayList<String> getDistanceString() {
        return distancetext;
    }

    public ArrayList<String> getDurationValue() {
        return durationvalue;
    }

    public ArrayList<String> getDurationString() {
        return durationtext;
    }


    public int totalTime(){
        int total = 0;
        for(int i = 0; i<list.size(); i++){
            Bundle dur = (Bundle) list.get(i).get("duration");
            int durr = Integer.valueOf(dur.getString("value"));
            total = total + durr;
        }
        return total;
    }

    public void setNoPoly(){
        polylines.add("-1");
    }

    public void setNoDistanceValue() {
        distancevalue.add("-1");
    }

    public void setNoDistanceString() {
         distancetext.add("-1");
    }

    public void setNoDurationValue() {
        durationvalue.add("-1");
    }

    public void setNoDurationString() {
        durationtext.add("-1");
    }

}
