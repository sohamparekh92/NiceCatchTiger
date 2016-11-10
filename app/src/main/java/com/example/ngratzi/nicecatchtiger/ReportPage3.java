package com.example.ngratzi.nicecatchtiger;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ReportPage3 extends AppCompatActivity {

    int report;
    int involve;
    String description;
    String roomNumber;
    int Department;
    int Building;
    RadioGroup radioGroup;

    //Page 3
    int Designation;
    String NAME;
    String EMAIL;
    String PN;

    int facultyID = 1;
    int staffID = 2;
    int studentID = 3;
    int otherID = 4;
    int designation;

    RadioButton faculty, staff, student, other;
    EditText Name, Email, PhoneNumber;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page3);

        /*faculty = (RadioButton) findViewById(R.id.fauclty);
        staff = (RadioButton) findViewById(R.id.staff);
        student = (RadioButton) findViewById(R.id.student);
        other = (RadioButton) findViewById(R.id.other);*/

        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        PhoneNumber = (EditText) findViewById(R.id.phoneNumber);

        final EditText otherDesignation = (EditText) findViewById(R.id.other9);
        otherDesignation.setVisibility(View.INVISIBLE);

        //What does this involve?

        ArrayList<RadioButton> designationButtons = new ArrayList<>();
        ArrayList<String> designationList = new ArrayList<>();
        designationList = FormData.getInstance().getFormData("personKinds","personKind");

        for(int i=0; i<designationList.size();++i){
            designationButtons.add(new RadioButton(this));
            designationButtons.get(i).setText(designationList.get(i));
        }

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        for(int i=0; i<designationButtons.size();++i){
            radioGroup.addView(designationButtons.get(i));
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton temp = (RadioButton) findViewById(checkedId);
                if (temp.getText().toString().equals("Other")) {
                    otherDesignation.setVisibility(View.VISIBLE);
                } else {
                    otherDesignation.setVisibility(View.INVISIBLE);
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void clickSubmit(View view) throws ExecutionException, InterruptedException, JSONException {

        int error = 0;

            //Page 3
            Designation = designation;
            NAME = Name.getText().toString();
            EMAIL = Email.getText().toString();
            PN = PhoneNumber.getText().toString();

            String designationString = "";
            String nameString = "";
            String emailString = "";
            String phoneString = "";

            EditText otherfield = (EditText) findViewById(R.id.other9);

            if(otherfield.getText().toString().equals("")){
                RadioButton temp = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                designationString = temp.getText().toString();
            }
            else{
                designationString = otherfield.getText().toString();
            }
            nameString = Name.getText().toString();
            emailString = Email.getText().toString();
            phoneString = PhoneNumber.getText().toString();

        if(designationString.equals("")){
            error = 1;
        }

        if(error == 0) {

            FormData.getInstance().addFormData("personKind",designationString);
            FormData.getInstance().addFormData("username",emailString);
            FormData.getInstance().addFormData("name",nameString);
            FormData.getInstance().addFormData("phone",phoneString);

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportTime = sdf.format(new Date());

            Log.i("Curr Date Time",reportTime);

            FormData.getInstance().addFormData("reportTime",reportTime);


            ExternalDBHandler externalDBHandler = new ExternalDBHandler();
            String response = externalDBHandler.execute("submitReportURLConn", new JSONObject(getReportMap()).toString()).get();
            Log.i("Response Page 3", response);
            JSONObject responseJSON = new JSONObject(response);
            String id = responseJSON.getJSONObject("data").getString("id");
            ExternalDBHandler photoUploadHandler = new ExternalDBHandler();
            if(FormData.getPictureTaken()) {
                FormData.setPictureTaken(false);
                photoUploadHandler.execute("uploadPhotoBase64", id);
            }
            Log.i("Response Page 3", id);
            startActivity(new Intent(ReportPage3.this, SucessSubmit.class));

        }
        else {
            Toast.makeText(getApplicationContext(), "Please Fill In All The Required Fields.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ReportPage3 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ngratzi.nicecatchtiger/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ReportPage3 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ngratzi.nicecatchtiger/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private HashMap<String, String> getReportMap(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("personKindID", Integer.toString(Designation));
        params.put("personName", NAME);
        params.put("personPhone", PN);
        params.put("personEmail", EMAIL);
        params.put("buildingID", Integer.toString(Building));
        params.put("room", roomNumber);
        params.put("description", description);
        params.put("involvementKindID", Integer.toString(involve));
        params.put("reportKindID", Integer.toString(report));
        params.put("departmentID", Integer.toString(Department));
        params.put("latitude", "0");
        params.put("logitude", "0");
        params.put("photoPath", "0");
        return params;
    }
}
