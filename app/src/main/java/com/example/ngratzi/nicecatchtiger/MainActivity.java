package com.example.ngratzi.nicecatchtiger;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_main);
    }


    public void startReport(View view) {
        startActivity(new Intent(MainActivity.this, ReportPage1.class));
    }

    public void startInfo(View view) {
        startActivity(new Intent(MainActivity.this, info.class));

    }
}
