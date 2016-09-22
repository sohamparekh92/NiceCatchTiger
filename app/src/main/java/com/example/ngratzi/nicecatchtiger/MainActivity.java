package com.example.ngratzi.nicecatchtiger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startReport(View view) {
        startActivity(new Intent(MainActivity.this, ReportPage1.class));
    }

    public void startAdminLogin(View view) {
        startActivity(new Intent(MainActivity.this, AdminLogin.class));
    }

    public void startMap(View view) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));

    }

    public void startInfo(View view) {
        startActivity(new Intent(MainActivity.this, info.class));

    }
}
