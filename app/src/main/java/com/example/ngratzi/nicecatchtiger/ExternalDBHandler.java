package com.example.ngratzi.nicecatchtiger;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.google.ads.AdRequest.LOGTAG;

/**
 * Created by Soham on 10/5/2016.
 */

public class ExternalDBHandler extends  AsyncTask< String ,Void,String> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)


    private String getParams() throws UnsupportedEncodingException {
        String params=  URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode("HeyBro","UTF-8")+"&"
                +URLEncoder.encode("involvementKind","UTF-8")+"="+URLEncoder.encode("Equipment","UTF-8")+"&"
                +URLEncoder.encode("reportKind","UTF-8")+"="+URLEncoder.encode("CloseCall","UTF-8")+"&"
                +URLEncoder.encode("buildingName","UTF-8")+"="+URLEncoder.encode("BRC","UTF-8")+"&"
                +URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode("12","UTF-8")+"&"
                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode("josh","UTF-8")+"&"
                +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode("gta","UTF-8")+"&"
                +URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode("","UTF-8")+"&"
                +URLEncoder.encode("department","UTF-8")+"="+URLEncoder.encode("Architecture","UTF-8")+"&"
                +URLEncoder.encode("reportTime","UTF-8")+"="+URLEncoder.encode("2016-10-12 13:28:09","UTF-8")+"&"
                +URLEncoder.encode("statusID","UTF-8")+"="+URLEncoder.encode("1","UTF-8")+"&"
                +URLEncoder.encode("actionTaken","UTF-8")+"="+URLEncoder.encode("lol","UTF-8")+"&"
                +URLEncoder.encode("incidentTime","UTF-8")+"="+URLEncoder.encode("2016-10-12 13:28:09","UTF-8")+"&"
                +URLEncoder.encode("isIOS","UTF-8")+"="+URLEncoder.encode("0","UTF-8");
        return params;
    }
    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        String url = "https://people.cs.clemson.edu/~jacosta/api/v1/reports";
        //String url = "https://people.cs.clemson.edu/~sohamap/nct_test/nct.php";
        String home_url = "https://people.cs.clemson.edu/~jacosta/api/v1/";
        String url_buildings = "buildings";
        URL urlObj;


        if(method.equals("submitReportURLConn")) {
            try {

                HttpURLConnection urlConn;
                DataOutputStream printout;
                DataInputStream input;
                urlObj = new URL(url);
                urlConn = (HttpURLConnection) urlObj.openConnection();
                urlConn.setDoOutput(true);
                urlConn.setRequestMethod("POST");
                urlConn.connect();
                JSONObject jsonParam = new JSONObject();
                JSONObject jsonOuter = new JSONObject();
                jsonParam.put("ID", "25");
                jsonParam.put("description", "Real");
                jsonParam.put("enable", "true");

                jsonOuter.put("data", jsonParam);

                String urlParameters =
                        URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode("Refined inputstream","UTF-8")+"&"
                                +URLEncoder.encode("involvementKind","UTF-8")+"="+URLEncoder.encode("Equipment","UTF-8")+"&"
                                +URLEncoder.encode("reportKind","UTF-8")+"="+URLEncoder.encode("Close Call","UTF-8")+"&"
                                +URLEncoder.encode("buildingName","UTF-8")+"="+URLEncoder.encode("BRC","UTF-8")+"&"
                                +URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode("12","UTF-8")+"&"
                                +URLEncoder.encode("personKind","UTF-8")+"="+URLEncoder.encode("Faculty","UTF-8")+"&"
                                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode("Donald Trump","UTF-8")+"&"
                                +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode("djtrump","UTF-8")+"&"
                                +URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode("","UTF-8")+"&"
                                +URLEncoder.encode("department","UTF-8")+"="+URLEncoder.encode("Architecture","UTF-8")+"&"
                                +URLEncoder.encode("reportTime","UTF-8")+"="+URLEncoder.encode("2016-10-12 13:28:09","UTF-8")+"&"
                                +URLEncoder.encode("statusID","UTF-8")+"="+URLEncoder.encode("1","UTF-8")+"&"
                                +URLEncoder.encode("actionTaken","UTF-8")+"="+URLEncoder.encode("action","UTF-8")+"&"
                                +URLEncoder.encode("incidentTime","UTF-8")+"="+URLEncoder.encode("2016-10-12 13:28:09","UTF-8")+"&"
                                +URLEncoder.encode("isIOS","UTF-8")+"="+URLEncoder.encode("0","UTF-8");

                //Sample string below works with no spaces, use for testing purpose
                //String reportJSONString = "description=NoSpace&involvementKind=Equipment&reportKind=Equipment&buildingName=BRC&room=123&personKind=Faculty&name=Joey&username=jacosta&phone=&department=Architecture&reportTime=&statusID=1&actionTaken=&incidentTime=&isIOS=0";

                printout = new DataOutputStream(urlConn.getOutputStream());
                printout.writeBytes(urlParameters);
                printout.flush();
                printout.close();

                Log.i("Http Response message", urlConn.getResponseMessage());
                Log.i("Http Response code", urlConn.getResponseCode()+"");
                InputStream inputStream = urlConn.getInputStream() ;
                if(inputStream==null){
                    inputStream = urlConn.getErrorStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while( (line = bufferedReader.readLine())!=null ){
                    response+=line;
                }
                bufferedReader.close();
                inputStream.close();

                return response;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if(method.equals("submitReportClient")) {
            String response = "";

            try {

                String reportJSONString = "description=123&involvementKind=Equipment&reportKind=Equipment&buildingName=BRC&room=123&personKind=Faculty&name=Joey&username=jacosta&phone=&department=Architecture&reportTime=&statusID=1&actionTaken=&incidentTime=&isIOS=0";

                JSONObject obj2 = new JSONObject();
                obj2.put("description","123");
                obj2.put("involvementKind","Equipment");
                obj2.put("reportKind","Close Call");
                obj2.put("buildingName","BRC");
                obj2.put("room","123");
                obj2.put("personKind","Faculty");
                obj2.put("name","Joey2");
                obj2.put("username","jacosta");
                obj2.put("phone","1234569874");
                obj2.put("department","Architecture");
                obj2.put("reportTime","2016-10-12 13:28:09");
                obj2.put("statusID","1");
                obj2.put("actionTaken","");
                obj2.put("incidentTime","2016-10-12 13:28:01");
                obj2.put("isIOS","0");

                Log.i("JSON Object String", obj2.toString());

                //ByteArrayEntity entity = new ByteArrayEntity(obj2.toString().getBytes("UTF-8"));
                //entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Accept", "application/json");
                //httpPost.setEntity(new StringEntity(obj2.toString(), "UTF-8"));

                String sample = "description=123&involvementKind=Work Practice/Procedure&reportKind=Close Call&buildingName=BRC&room=123&personKind=Faculty&name=Joey&username=jacosta&phone=&department=Architecture&reportTime=2016-10-12 13:28:09&statusID=1&actionTaken=&incidentTime=2016-10-12 13:28:01&isIOS=1";

                List<NameValuePair> myList = new ArrayList<NameValuePair>();
                myList.add( new BasicNameValuePair("description","bro"));
                myList.add( new BasicNameValuePair("pi","brso"));
                myList.add( new BasicNameValuePair("gi","brsao"));

                StringEntity se =  new StringEntity(sample, "UTF-8");
                StringEntity ne = new StringEntity(sample);
                StringEntity je = new StringEntity(obj2.toString(), "UTF-8");
                StringEntity nje = new StringEntity(obj2.toString());
                StringEntity parax = new StringEntity(getParams(), "UTF-8");
                StringEntity nparax = new StringEntity(getParams());


                Log.i("String Entity",EntityUtils.toString(new UrlEncodedFormEntity(myList) ) );
                httpPost.setEntity(nparax);

                //httpPost.setEntity(entity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                return response;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(LOGTAG, "POST Response >> " + response);
            return response;
        }


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
            //String reportJSONString = params[1];
            //String reportJSONString = "description=123&involvementKind=Work Practice/Procedure&reportKind=Close Call&buildingName=BRC&room=123&personKind=Faculty&name=Joey&username=jacosta&phone=&department=Architecture&reportTime=2016-10-12 13:28:09&statusID=1&actionTaken=&incidentTime=2016-10-12 13:28:01&isIOS=0";
            try {
                urlObj = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                httpURLConnection.setRequestMethod("POST");
                JSONObject obj2 = new JSONObject();
                obj2.put("description","123");
                obj2.put("involvementKind","Work Practice/Procedure");
                obj2.put("reportKind","Close Call");
                obj2.put("buildingName","BRC");
                obj2.put("room","123");
                obj2.put("personKind","Faculty");
                obj2.put("name","Joey2");
                obj2.put("username","jacosta");
                obj2.put("phone","1234569874");
                obj2.put("department","Architecture");
                obj2.put("reportTime","2016-10-12 13:28:09");
                obj2.put("statusID","1");
                obj2.put("actionTaken","123");
                obj2.put("incidentTime","2016-10-12 13:28:01");
                obj2.put("isIOS","0");
                //httpURLConnection.setRequestProperty("Content-Type", "application/json");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream OS = httpURLConnection.getOutputStream();
                Log.i("Http response Message", httpURLConnection.getResponseMessage());
                Log.i("Http response Code", httpURLConnection.getResponseCode()+"");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = obj2.toString();
                Log.i("URL_OUTPUT", data);
                bufferedWriter.write(data);
                //bufferedWriter.flush();
                //bufferedWriter.close();
               // OS.close();
                Log.i("End of","os");

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
                Log.i("End of","submitReport");
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (method.equals("getBuildings")){
            try {
                URL urlB = new URL(home_url+url_buildings);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)urlB.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                //httpsURLConnection.setDoOutput(true);
                InputStream IS = httpsURLConnection.getInputStream();
                int status = httpsURLConnection.getResponseCode();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                String response = "";
                String line = "";
                while( (line = bufferedReader.readLine())!=null ){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpsURLConnection.disconnect();
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
            Log.i("Response", s);
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
