package com.example.ngratzi.nicecatchtiger;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

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
    private static boolean uploadedPhoto = false;
    private static byte [] imageBytes;
    private static boolean imageTaken = false;
    private static ArrayList<Context> activities = new ArrayList<>();

    private FormData(){
        myReport.put("incidentTime","2016-10-12 13:28:09");
        myReport.put("reportTime","2016-10-12 13:28:09");
        myReport.put("statusID","1");
        myReport.put("actionTaken","none");
    }

    public static void setUploadedPhoto(boolean value){
        Log.i("Set Upload Photo Activated in From Data Class","True");
        uploadedPhoto = value;
    }

    public void addActivity(Context context){
        activities.add(context);
    }

    public void clearActivities(){
        for(Context current: activities){
            PreferenceManager.getDefaultSharedPreferences(current).edit().clear().apply();
        }
    }

    public static boolean getUploadedPhoto(){
        return uploadedPhoto;
    }

    static FormData getInstance(){
        return instance;
    }

    static public void setImageBytes(byte[] bytes){
        imageBytes = bytes;
    }

    static public byte [] getImageBytes(){
        return imageBytes;
    }

    public static boolean getPictureTaken(){
        return imageTaken;
    }

    public static void setPictureTaken(boolean value){
        imageTaken = value;
    }

    public void addFormData(String name, String value){
        //Log.i("Form Data Added",name+" : "+value);
        myReport.put(name,value);
    }

    public String getFormElement(String key){
        String result = "";
        try{
            result = myReport.get(key);
        }
        catch (Exception e){
            Log.i("Exception caught", "FormData null key");
            result = "na";
        }
        return result;
    }

    public boolean isFormComplete(){
        return true;
    }

    public ArrayList<String> getFormData(String elements, String element){

        ArrayList<String> resultList = new ArrayList<String>();
        try {
            String result = null;
            //result = new ExternalDBHandler().execute("getData", elements).get();
            result = new ExternalDBHandler().execute("getDataClient",elements).get();

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
