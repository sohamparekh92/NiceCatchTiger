package com.example.ngratzi.nicecatchtiger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ReportPage1 extends AppCompatActivity {

    //Managers
    private InputMethodManager inputMethodManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    //Parameters
    public static int Report, Involve;


    //ID Values
    //Check the appropriate report
    int closeCall = 1;
    int lessonLearned = 2;
    int safetyIssue = 3;
    int otherReportID = 4;


    //What does this involve
    int work_practice_procedure = 1;
    int chemical = 2;
    int equipment = 3;
    int work_space_condition = 4;
    int radiation = 5;
    int biological = 6;
    int otherInvolveID = 7;


    //Initialize

    RadioButton closeCallRb, lessonLearnedRb, safetyIssueRb, other1Rb, workPracticeProcedureRb,
            chemicalRb, equipmentRb, workSpaceConditionRb, radiationRb, biologicalRb, other2Rb;
    EditText otherReport, otherInvolve, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Before","Super oncreate");
        super.onCreate(savedInstanceState);
        Log.i("Before","set content");
        //setContentView(R.layout.activity_report_page1);
        setContentView(R.layout.activity_report_page1_1);
        Log.i("Before","Actionbar stuf");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7800c9")));
        actionBar.setTitle("Nice Catch Tiger!");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Log.i("Before","Page start");
        otherReport = (EditText) findViewById(R.id.otherReport);

        inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        ArrayList<RadioButton> buttonsReport = new ArrayList<>();
        ArrayList<String> reportKindList = FormData.getInstance().getFormData("reportKinds","reportKind");
        for(int i=0; i<reportKindList.size();++i){
            buttonsReport.add(new RadioButton(this));
            buttonsReport.get(i).setText(reportKindList.get(i));
        }

        description = (EditText)findViewById(R.id.description);


        otherReport.setVisibility(View.INVISIBLE);


        ArrayList<RadioButton> buttonsInvolve = new ArrayList<>();
        ArrayList<String> involveKindList = FormData.getInstance().getFormData("involvements", "involvementKind");
        if(involveKindList.isEmpty()){
            Toast.makeText(this,"Please Check Your Internet Connection.",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        //reportKindList.remove(0);
        for (int i = 0; i < involveKindList.size(); ++i) {
            buttonsInvolve.add(new RadioButton(this));
            buttonsInvolve.get(i).setText(involveKindList.get(i));
        }
        otherInvolve = (EditText) findViewById(R.id.otherInvolve);
        inputMethodManager.hideSoftInputFromInputMethod(otherInvolve.getWindowToken(),0);
        otherInvolve.setVisibility(View.INVISIBLE);


        //Check the appropriate report
        RadioGroup radioGroupReport = (RadioGroup) findViewById(R.id.ReportRG);
        for(int i=0; i<buttonsReport.size();++i) {
            radioGroupReport.addView(buttonsReport.get(i));
        }

        radioGroupReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton curr = (RadioButton)findViewById(checkedId);
                if (curr.getText().equals("Other")) {
                    otherReport.setVisibility(View.VISIBLE);
                } else {
                    otherReport.setVisibility(View.INVISIBLE);
                }
            }
        });

        //What does this involve?
        RadioGroup radioGroupInvolve = (RadioGroup) findViewById(R.id.InvolveRG);
        for(int i=0; i<buttonsInvolve.size();++i) {
            radioGroupInvolve.addView(buttonsInvolve.get(i));
        }
        radioGroupInvolve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton curr = (RadioButton)findViewById(checkedId);
                if (curr.getText().equals("Other")) {
                    otherInvolve.setVisibility(View.VISIBLE);
                } else {
                    otherInvolve.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    public void showInfo(View view){
        new AlertDialog.Builder(this)
                .setTitle("Report Definitions")
                .setMessage("Close Call - A situation that could have led to an injury or" +
                        "property damage, but did not.\n\n" +
                        "Lesson Learned - Knowledge gained from a positive or negative experience.\n\n" +
                        "Safety Issue - Any action observed or participated in that can lead to an injury or " +
                        "property damage.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

    public void showInfoInvolve(View view){
        new AlertDialog.Builder(this)
                .setTitle("Report Definitions")
                .setMessage("Close Call - A situation that could have led to an injury or" +
                        "property damage, but did not.\n\n" +
                        "Lesson Learned - Knowledge gained from a positive or negative experience.\n\n" +
                        "Safety Issue - Any action observed or participated in that can lead to an injury or " +
                        "property damage.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }


    //Next Button pressed
    public void startPage2(View view) {

        //Error Flag
        int error = 0;

        RadioGroup radioGroupInvolve = (RadioGroup) findViewById(R.id.InvolveRG);
        RadioGroup radioGroupReport = (RadioGroup) findViewById(R.id.ReportRG);

        RadioButton checkedButtonReport = (RadioButton) findViewById(radioGroupReport.getCheckedRadioButtonId());
        RadioButton checkedButtonInvolve = (RadioButton) findViewById(radioGroupInvolve.getCheckedRadioButtonId());
        String checkedValueReport = "";
        String checkedValueInvolved = "";

        try {
            checkedValueInvolved = checkedButtonInvolve.getText().toString();
            checkedValueReport = checkedButtonReport.getText().toString();
        }
        catch (Exception e){
            error = 1;
        }
        if(description.getText().toString().equals("")){
            error = 1;
        }

        if (error == 0) {
            FormData.getInstance().addFormData("description",description.getText().toString());
            FormData.getInstance().addFormData("involvementKind",checkedValueInvolved);
            FormData.getInstance().addFormData("reportKind",checkedValueReport);
            startActivity(new Intent(ReportPage1.this, ReportPage2.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please Fill In All Fields.", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveRG(SharedPreferences.Editor se, RadioGroup rg){
        int number = rg.getChildCount();
        int i = 0;
        while(number!=0) {
            RadioButton currentButton = (RadioButton) rg.getChildAt(i);
            se.putBoolean("A"+number, currentButton.isChecked());
            number--;
            i++;
        }
        se.commit();
    }

    private void restoreRG(SharedPreferences sp, RadioGroup rg ){
        int number = rg.getChildCount();
        int i = 0;
        while(number!=0) {
            RadioButton currentButton = (RadioButton) rg.getChildAt(i);
            boolean value = sp.getBoolean("A"+number, false);
            currentButton.setChecked(value);
            number--;
            i++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Save Involve Group
        RadioGroup radioGroupInvolve = (RadioGroup) findViewById(R.id.InvolveRG);
        sharedPreferences = getSharedPreferences("RadioInvolve",0);
        sharedPreferencesEditor = sharedPreferences.edit();
        saveRG(sharedPreferencesEditor,radioGroupInvolve);

        //Save Other Involve
        sharedPreferences = getSharedPreferences("OtherInvolve",0);
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("otherInvolve",otherInvolve.getText().toString());
        sharedPreferencesEditor.commit();

        //Save Other Report
        sharedPreferences = getSharedPreferences("OtherReport",0);
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("otherReport",otherReport.getText().toString());
        sharedPreferencesEditor.commit();


        //Save Report Group
        RadioGroup radioGroupReport = (RadioGroup) findViewById(R.id.ReportRG);
        sharedPreferences = getSharedPreferences("RadioReport",0);
        sharedPreferencesEditor = sharedPreferences.edit();
        saveRG(sharedPreferencesEditor,radioGroupReport);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Restore Involve Group
        RadioGroup radioGroupInvolve = (RadioGroup) findViewById(R.id.InvolveRG);
        sharedPreferences = getSharedPreferences("RadioInvolve",0);
        restoreRG(sharedPreferences,radioGroupInvolve);

        //Restore Other Involve
        sharedPreferences = getSharedPreferences("OtherInvolve",0);
        otherInvolve.setText(sharedPreferences.getString("otherInvolve",""), TextView.BufferType.EDITABLE);


        //Restore Other Report
        sharedPreferences = getSharedPreferences("OtherReport",0);
        otherInvolve.setText(sharedPreferences.getString("otherReport",""), TextView.BufferType.EDITABLE);



        RadioGroup radioGroupReport = (RadioGroup) findViewById(R.id.ReportRG);
        sharedPreferences = getSharedPreferences("RadioReport",0);
        restoreRG(sharedPreferences,radioGroupReport);

    }

}
