package com.example.ngratzi.nicecatchtiger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class ReportPage1 extends AppCompatActivity {

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
    EditText otherReport, otherInvolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page1);

        otherReport = (EditText) findViewById(R.id.otherReport);

        ArrayList<RadioButton> buttonsReport = new ArrayList<>();
        ArrayList<String> reportKindList = FormData.getInstance().getFormData("reportKinds","reportKind");
        //reportKindList.remove(0);
        for(int i=0; i<reportKindList.size();++i){
            buttonsReport.add(new RadioButton(this));
            buttonsReport.get(i).setText(reportKindList.get(i));
        }


        otherReport.setVisibility(View.INVISIBLE);
        //What does this involve?
        /*
        workPracticeProcedureRb = (RadioButton) findViewById(R.id.workPractiveProcedure);
        chemicalRb = (RadioButton) findViewById(R.id.chemical);
        equipmentRb = (RadioButton) findViewById(R.id.equipment);
        workSpaceConditionRb = (RadioButton) findViewById(R.id.workSpaceCondition);
        radiationRb = (RadioButton) findViewById(R.id.radiation);
        biologicalRb = (RadioButton) findViewById(R.id.biological);
        other2Rb = (RadioButton) findViewById(R.id.other2); */

         ArrayList<RadioButton> buttonsInvolve = new ArrayList<>();
        ArrayList<String> involveKindList = FormData.getInstance().getFormData("involvements","involvementKind");
        //reportKindList.remove(0);
        for(int i=0; i<involveKindList.size();++i){
            buttonsInvolve.add(new RadioButton(this));
            buttonsInvolve.get(i).setText(involveKindList.get(i));
        }
        otherInvolve = (EditText) findViewById(R.id.otherInvolve);

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


    //Next Button pressed
    public void startPage2(View view) {

        //Error Flag
        int error = 0;

        RadioGroup radioGroupInvolve = (RadioGroup) findViewById(R.id.InvolveRG);
        RadioGroup radioGroupReport = (RadioGroup) findViewById(R.id.ReportRG);

        RadioButton checkedButtonReport = (RadioButton) findViewById(radioGroupReport.getCheckedRadioButtonId());
        String checkedValueReport = checkedButtonReport.getText().toString();

        RadioButton checkedButtonInvolve = (RadioButton) findViewById(radioGroupInvolve.getCheckedRadioButtonId());
        String checkedValueInvolved = checkedButtonInvolve.getText().toString();

        if(checkedValueInvolved.equals("") || checkedButtonReport.equals("")){
            error = 1;
        }
        else{
            error = 0;
            FormData.getInstance().addFormData("involvementKind",checkedValueInvolved);
            FormData.getInstance().addFormData("reportKind",checkedValueReport);
        }



        /*

        //Check the appropriate report
        if (closeCallRb.isChecked()) {
            Report = closeCall;
        } else if (lessonLearnedRb.isChecked()) {
            Report = lessonLearned;
        } else if (safetyIssueRb.isChecked()) {
            Report = safetyIssue;
        }
        //Need to make changes
        else if (other1Rb.isChecked()) {
            if (!otherReport.getText().toString().isEmpty()) {
                //Report = otherReport.getText().toString();
                Report = otherReportID;
            } else {
                error = 1;
            }
        }
        else {
            error = 1;
        }

        //What does this involve
        if (workPracticeProcedureRb.isChecked()) {
            Involve = work_practice_procedure;
        } else if (chemicalRb.isChecked()) {
            Involve = chemical;
        } else if (equipmentRb.isChecked()) {
            Involve = equipment;
        } else if (workSpaceConditionRb.isChecked()) {
            Involve = work_space_condition;
        } else if (radiationRb.isChecked()) {
            Involve = radiation;
        } else if (biologicalRb.isChecked()) {
            Involve = biological;
        }
        //Need to make changes
        else if (other2Rb.isChecked()) {
            if (!otherInvolve.getText().toString().isEmpty()) {
                //Involve = otherInvolve.getText().toString();
                Involve = otherInvolveID;
            } else {
                error = 1;
            }
        }
        else {
            error = 1;
        }*/




        if (error == 0) {
            startActivity(new Intent(ReportPage1.this, ReportPage2.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please Fill In ALl Fields!", Toast.LENGTH_SHORT).show();
        }

    }

}
