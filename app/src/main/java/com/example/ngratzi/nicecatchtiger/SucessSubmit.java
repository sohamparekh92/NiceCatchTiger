package com.example.ngratzi.nicecatchtiger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SucessSubmit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess_submit);
    }

    public void clickHome(View view) {
        startActivity(new Intent(SucessSubmit.this, MainActivity.class));
    }
}
