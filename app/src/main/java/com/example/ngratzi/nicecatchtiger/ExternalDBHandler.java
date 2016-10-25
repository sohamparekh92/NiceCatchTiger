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

                FormData formInstance = FormData.getInstance();

                String urlParameters =
                        URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode("Mid form submit Test","UTF-8")+"&" //done
                                +URLEncoder.encode("involvementKind","UTF-8")+"="+URLEncoder.encode("Equipment","UTF-8")+"&" //done
                                +URLEncoder.encode("reportKind","UTF-8")+"="+URLEncoder.encode("Close Call","UTF-8")+"&"  //done
                                +URLEncoder.encode("buildingName","UTF-8")+"="+URLEncoder.encode("BRC","UTF-8")+"&" //done
                                +URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode("12","UTF-8")+"&" //done
                                +URLEncoder.encode("personKind","UTF-8")+"="+URLEncoder.encode("Faculty","UTF-8")+"&" //done
                                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode("Donald Trump","UTF-8")+"&" //done
                                +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode("djtrump","UTF-8")+"&"//done
                                +URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode("123456789","UTF-8")+"&" //done
                                +URLEncoder.encode("department","UTF-8")+"="+URLEncoder.encode("Architecture","UTF-8")+"&" //done
                                +URLEncoder.encode("reportTime","UTF-8")+"="+URLEncoder.encode("2016-10-12 13:28:09","UTF-8")+"&"
                                +URLEncoder.encode("statusID","UTF-8")+"="+URLEncoder.encode("1","UTF-8")+"&"
                                +URLEncoder.encode("actionTaken","UTF-8")+"="+URLEncoder.encode("action","UTF-8")+"&"
                                +URLEncoder.encode("incidentTime","UTF-8")+"="+URLEncoder.encode("2016-10-12 13:28:09","UTF-8")+"&"
                                +URLEncoder.encode("isIOS","UTF-8")+"="+URLEncoder.encode("0","UTF-8");


                String urlParameters2 =
                        URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("description"),"UTF-8")+"&"
                                +URLEncoder.encode("involvementKind","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("involvementKind"),"UTF-8")+"&" //done
                                +URLEncoder.encode("reportKind","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("reportKind"),"UTF-8")+"&"  //done
                                +URLEncoder.encode("buildingName","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("buildingName"),"UTF-8")+"&"
                                +URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("room"),"UTF-8")+"&"
                                +URLEncoder.encode("personKind","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("personKind"),"UTF-8")+"&"
                                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("name"),"UTF-8")+"&"
                                +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("username"),"UTF-8")+"&"
                                +URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("phone"),"UTF-8")+"&"
                                +URLEncoder.encode("department","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("department"),"UTF-8")+"&"
                                +URLEncoder.encode("reportTime","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("reportTime"),"UTF-8")+"&"
                                +URLEncoder.encode("statusID","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("statusID"),"UTF-8")+"&"
                                +URLEncoder.encode("actionTaken","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("actionTaken"),"UTF-8")+"&"
                                +URLEncoder.encode("incidentTime","UTF-8")+"="+URLEncoder.encode(formInstance.getFormElement("incidentTime"),"UTF-8")+"&"
                                +URLEncoder.encode("isIOS","UTF-8")+"="+URLEncoder.encode("0","UTF-8"); //done

                Log.i("URL Parameters2",urlParameters2);
                Log.i("URL Parameters",urlParameters);


                //Sample string below works with no spaces, use for testing purpose
                //String reportJSONString = "description=NoSpace&involvementKind=Equipment&reportKind=Equipment&buildingName=BRC&room=123&personKind=Faculty&name=Joey&username=jacosta&phone=&department=Architecture&reportTime=&statusID=1&actionTaken=&incidentTime=&isIOS=0";

                printout = new DataOutputStream(urlConn.getOutputStream());
                printout.writeBytes(urlParameters2);
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

        if (method.equals("getData")){
            try {
                String elementsType = params[1];
                String final_url = home_url + elementsType;
                URL url_get = new URL(final_url);
                Log.i("Final URL", final_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url_get.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
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
                Log.i("End of","getData");
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
            Log.i("Response Recieved",s);
        }
        if(s==null){
            Log.i("Response Recieved", "NULL");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
