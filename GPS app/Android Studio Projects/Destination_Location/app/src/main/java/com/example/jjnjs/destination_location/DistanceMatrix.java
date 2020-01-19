package com.example.jjnjs.destination_location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistanceMatrix {

    ArrayList<Integer> values = new ArrayList<>();
    ArrayList<LatLng> locations = new ArrayList<>();
    int indexQuickest;
    public DistanceMatrix(JSONObject json, ArrayList<LatLng> origins){
        for(int i = 0; i<origins.size();i++){
            locations.add(origins.get(i));
        }
        try {
            JSONArray options = json.getJSONArray("rows");
            for(int i = 0; i<options.length(); i++){
                JSONObject place = options.getJSONObject(i);
                JSONArray elements = place.getJSONArray("elements");
                JSONObject elementsobject = elements.getJSONObject(0);
                JSONObject distance = elementsobject.getJSONObject("distance");
                int value = distance.getInt("value");
                values.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(locations.isEmpty()){
            indexQuickest = -1;
        }else{
            setIndexQuickest();
        }
    }

    private void setIndexQuickest() {
        int lowest = values.get(0);
        int index = 0;
        for(int i = 0; i<values.size(); i++){
            if(values.get(i)<lowest){
                index = i;
                lowest = values.get(i);
            }
        }
        indexQuickest = index;
    }

    public LatLng getQuickest(){
        if(indexQuickest != -1){
            return locations.get(indexQuickest);
        }else{
            LatLng j = new LatLng(1000,1000);
            return j;
        }
    }
}
