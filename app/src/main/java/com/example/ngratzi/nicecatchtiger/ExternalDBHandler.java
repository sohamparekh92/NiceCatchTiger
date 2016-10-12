package com.example.ngratzi.nicecatchtiger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Soham on 10/5/2016.
 */

public class ExternalDBHandler extends  AsyncTask< String ,Void,String> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        String url = "https://people.cs.clemson.edu/~jacosta/api/v1/reports";
        String home_url = "https://people.cs.clemson.edu/~jacosta/api/v1/";
        String url_buildings = "buildings";

        String charset = "UTF-8";
        HttpURLConnection conn;
        //DataOutputStream wr;
        StringBuilder result;
        URL urlObj;
        JSONObject reportJSON;
        StringBuilder sbParams;
        String paramsString;

        if(method.equals("submitReport")) {

            Log.i("Entered", "Submitreport");
            Log.i("JSON_Object", params[1]);


            /*String reportJSONString =  "description=finalReportData" +
                    "&involvementKind=Chemical" +
                    "&reportKind=Lesson Learned" +
                    "&buildingName=Freeman Hall" +
                    "&room=123" +
                    "&personKind=Faculty" +
                    "&name=" +
                    "&username=" +
                    "&phone=" +
                    "&department=" +
                    "&reportTime=" +
                    "&statusID=1" +
                    "&actionTaken=" +
                    "&incidentTime=";*/
            String reportJSONString = params[1];
            // String reportJSONString = reportJSON.toString();
            try {
                urlObj = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                Log.i("Exception", "After connect");

                OutputStream OS = httpURLConnection.getOutputStream();
                Log.i("Exception", "After OS");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode(reportJSONString, "UTF-8");
                Log.i("URL_OUTPUT", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                String response = "";
                String line = "";
                while( (line = bufferedReader.readLine())!=null ){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("Exception", "MalinformedURL");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
                Log.i("Exception", "protocol");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Exception submit", "io");
            }

        }

        if (method.equals("getBuildings")){
            try {
                URL urlB = new URL(home_url+url_buildings);
                HttpURLConnection httpURLConnection = (HttpURLConnection)urlB.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                String response = "";
                String line = "";
                while( (line = bufferedReader.readLine())!=null ){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                Log.i("End of","getBuildings");
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("IOException Buildings","getBuildings");
                e.printStackTrace();
            }
        }
        return null;
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            Log.i("Error", "Yes Postexecute");
            if(s.equals("student_registration_success")) {
                Log.i("Error", "Success Postexecute");
            }
            else{
                Log.i("Error2", s);
            }
        }
        if(s==null){
            Log.i("Null Response", "Got null");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
