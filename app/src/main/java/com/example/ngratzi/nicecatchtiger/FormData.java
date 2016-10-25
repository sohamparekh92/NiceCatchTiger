package com.example.ngratzi.nicecatchtiger;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Soham on 10/23/2016.
 */

public class FormData {

    private HashMap<String,String> myReport = new HashMap<>();
    static FormData instance = new FormData();
    private FormData(){}
    static FormData getInstance(){
        return instance;
    }

    public void addFormData(String name, String value){
        myReport.put(name,value);
    }

    public String getFormElement(String key){
        return myReport.get(key);
    }

    public boolean isFormComplete(){
        return true;
    }

    public ArrayList<String> getFormData(String elements, String element){

        ArrayList<String> resultList = new ArrayList<String>();
        try {
            String result = new ExternalDBHandler().execute("getData",elements).get();
            JSONObject buildingJSON = new JSONObject(result);
            JSONArray data = buildingJSON.getJSONArray("data");
            for(int i=0; i<data.length();++i){
                resultList.add(data.getJSONObject(i).getString(element));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }

}