package com.example.ngratzi.nicecatchtiger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.vision.barcode.Barcode;

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

    int designation;

    private SharedPreferences sharedPreferences;

    RadioButton faculty, staff, student, other;
    AutoCompleteTextView Name, Email, PhoneNumber;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_report_page3);
        setContentView(R.layout.activity_report_page3_1);

        sharedPreferences = getSharedPreferences("Page3",0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7800c9")));
        actionBar.setTitle("Nice Catch Tiger!");
        actionBar.setDisplayHomeAsUpEnabled(true);



        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);

        Name = (InstaAutoComplete) findViewById(R.id.name);
        Email = (InstaAutoComplete) findViewById(R.id.email);
        PhoneNumber = (InstaAutoComplete) findViewById(R.id.phoneNumber);



        final EditText otherDesignation = (EditText) findViewById(R.id.other9);
        otherDesignation.setVisibility(View.INVISIBLE);

        //What does this involve?

        ArrayList<RadioButton> designationButtons = new ArrayList<>();
        ArrayList<String> designationList = new ArrayList<>();
        designationList = FormData.getInstance().getFormData("personKinds", "personKind");

        for (int i = 0; i < designationList.size(); ++i) {
            designationButtons.add(new RadioButton(this));
            designationButtons.get(i).setText(designationList.get(i));
        }

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        for (int i = 0; i < designationButtons.size(); ++i) {
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

        if (otherfield.getText().toString().equals("")) {
            RadioButton temp = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            designationString = temp.getText().toString();
        } else {
            designationString = otherfield.getText().toString();
        }
        nameString = Name.getText().toString();
        emailString = Email.getText().toString();
        phoneString = PhoneNumber.getText().toString();

        if (designationString.equals("") || nameString.equals("") || emailString.equals("")) {
            error = 1;
        }

        if (error == 0) {

            int delayTime = 2000;

            FormData.getInstance().addFormData("personKind", designationString);
            FormData.getInstance().addFormData("username", emailString);
            FormData.getInstance().addFormData("name", nameString);
            FormData.getInstance().addFormData("phone", phoneString);

            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportTime = sdf.format(new Date());

            Log.i("Curr Date Time", reportTime);

            FormData.getInstance().addFormData("reportTime", reportTime);


            ExternalDBHandler externalDBHandler = new ExternalDBHandler();
            //String response = externalDBHandler.execute("submitReportURLConn", new JSONObject(getReportMap()).toString()).get();
            String response = externalDBHandler.execute("submitReportURLConn").get();
            Log.i("Response Page 3", response);
            JSONObject responseJSON = new JSONObject(response);
            final String id = responseJSON.getJSONObject("data").getString("id");
            final ExternalDBHandler photoUploadHandler = new ExternalDBHandler();
            Toast.makeText(this, "Uploading Report", Toast.LENGTH_LONG).show();

            if (FormData.getPictureTaken()) {
                delayTime +=2000;

                Thread halt = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            photoUploadHandler.execute("uploadPhotoBase64", id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                halt.start();
                FormData.setPictureTaken(false);
                FormData.setUploadedPhoto(false);
            }
            Log.i("Response Page 3", id);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after seconds
                    startActivity(new Intent(ReportPage3.this, SucessSubmit.class));
                }
            }, delayTime);

        } else {
            Toast.makeText(getApplicationContext(), "Please Fill In All The Required Fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAutoComplete(final InstaAutoComplete autoComplete, String item){

        ArrayList<String> myList = new ArrayList<>();
        myList.add(item);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,myList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        autoComplete.setAdapter(adapter);
        autoComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InstaAutoComplete current = (InstaAutoComplete) view;
                if(!current.getText().toString().equals("")) {
                    autoComplete.showDropDown();
                }
                return false;
            }
        });
    }

    private void saveRG(SharedPreferences se, RadioGroup rg, String identifier){
        int number = rg.getChildCount();
        int i = 0;
        while(number!=0) {
            RadioButton currentButton = (RadioButton) rg.getChildAt(i);
            se.edit().putBoolean("A"+number+identifier, currentButton.isChecked()).commit();
            number--;
            i++;
        }
    }

    private void restoreRG(SharedPreferences sp, RadioGroup rg, String identifier ){
        int number = rg.getChildCount();
        int i = 0;
        while(number!=0) {
            RadioButton currentButton = (RadioButton) rg.getChildAt(i);
            boolean value = sp.getBoolean("A"+number+identifier, false);
            currentButton.setChecked(value);
            number--;
            i++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Save Designation
        saveRG(sharedPreferences,radioGroup, "RG");

        //Save Contact Info.
        sharedPreferences.edit().putString("name",Name.getText().toString()).apply();
        sharedPreferences.edit().putString("email", Email.getText().toString()).apply();
        sharedPreferences.edit().putString("phone", PhoneNumber.getText().toString()).apply();

        //Save Boolean
        sharedPreferences.edit().putBoolean("saved",true);

    }

    @Override
    protected void onResume() {
        super.onResume();



        //Restore Designation
        restoreRG(sharedPreferences,radioGroup, "RG");

        //Restore Contact Info.
        setAutoComplete((InstaAutoComplete) Name,sharedPreferences.getString("name",""));

        setAutoComplete((InstaAutoComplete) Email,sharedPreferences.getString("email",""));

        setAutoComplete((InstaAutoComplete) PhoneNumber,sharedPreferences.getString("phone",""));

    }
}