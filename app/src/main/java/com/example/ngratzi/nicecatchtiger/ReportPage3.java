package com.example.ngratzi.nicecatchtiger;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ReportPage3 extends AppCompatActivity {

    int report;
    int involve;
    String description;
    String roomNumber;
    int Department;
    int Building;

    //Page 3
    int Designation;
    String NAME;
    String EMAIL;
    String PN;

    JSONParser jsonParser = new JSONParser();

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

        faculty = (RadioButton) findViewById(R.id.fauclty);
        staff = (RadioButton) findViewById(R.id.staff);
        student = (RadioButton) findViewById(R.id.student);
        other = (RadioButton) findViewById(R.id.other);

        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        PhoneNumber = (EditText) findViewById(R.id.phoneNumber);

        final EditText otherDesignation = (EditText) findViewById(R.id.other9);
        otherDesignation.setVisibility(View.INVISIBLE);

        //What does this involve?
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.other) {
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

    public void clickSubmit(View view) throws JSONException {

        int error = 0;

        if (faculty.isChecked()) {
            designation = facultyID;
        } else if (staff.isChecked()) {
            designation = staffID;
        } else if (student.isChecked()) {
            designation = studentID;
        } else if (other.isChecked()) {
            designation = otherID;
            if(!other.getText().toString().isEmpty()) {

            }
            else {
                error = 1;
            }
        } else {
            error = 1;
        }



        if(error != 0) {
            Toast.makeText(getApplicationContext(), "Please Fill In ALl Fields!", Toast.LENGTH_SHORT).show();
        }
        else {
            //Page 1
            report = ReportPage1.Report;
            involve = ReportPage1.Involve;

            //Page 2
            description = ReportPage2.Description;
            roomNumber = ReportPage2.RNumber;
            Department = ReportPage2.department;
            Building = ReportPage2.building;

            //Page 3
            Designation = designation;
            NAME = Name.getText().toString();
            EMAIL = Email.getText().toString();
            PN = PhoneNumber.getText().toString();

            //IN PHONE
            new submitReport().execute();


            startActivity(new Intent(ReportPage3.this, SucessSubmit.class));
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


    class submitReport extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(String... args) {
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


            String url = "http://people.cs.clemson.edu/~sdprovo/AndroidProject/API/submitReport.php";
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            String msg = null;
            try {
                msg = json.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Note:", msg);
            return null;

        }

        protected void onPostExecute(Void file_url) {

        }


    }

}
