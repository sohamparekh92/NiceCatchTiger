package com.example.ngratzi.nicecatchtiger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


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

        //Check the appropriate report
        closeCallRb = (RadioButton) findViewById(R.id.closeCall);
        lessonLearnedRb = (RadioButton) findViewById(R.id.lessonLearned);
        safetyIssueRb = (RadioButton) findViewById(R.id.safetyIssue);
        other1Rb = (RadioButton) findViewById(R.id.other1);
        otherReport = (EditText) findViewById(R.id.otherReport);
        otherReport.setVisibility(View.INVISIBLE);

        //What does this involve?
        workPracticeProcedureRb = (RadioButton) findViewById(R.id.workPractiveProcedure);
        chemicalRb = (RadioButton) findViewById(R.id.chemical);
        equipmentRb = (RadioButton) findViewById(R.id.equipment);
        workSpaceConditionRb = (RadioButton) findViewById(R.id.workSpaceCondition);
        radiationRb = (RadioButton) findViewById(R.id.radiation);
        biologicalRb = (RadioButton) findViewById(R.id.biological);
        other2Rb = (RadioButton) findViewById(R.id.other2);
        otherInvolve = (EditText) findViewById(R.id.otherInvolve);
        otherInvolve.setVisibility(View.INVISIBLE);

        //Check the appropriate report
        RadioGroup radioGroupReport = (RadioGroup) findViewById(R.id.ReportRG);
        radioGroupReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.other1) {
                    otherReport.setVisibility(View.VISIBLE);
                } else {
                    otherReport.setVisibility(View.INVISIBLE);
                }
            }
        });

        //What does this involve?
        RadioGroup radioGroupInvolve = (RadioGroup) findViewById(R.id.InvolveRG);
        radioGroupInvolve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.other2){
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
        }


        if (error == 0) {
            startActivity(new Intent(ReportPage1.this, ReportPage2.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please Fill In ALl Fields!", Toast.LENGTH_SHORT).show();
        }

    }

}
