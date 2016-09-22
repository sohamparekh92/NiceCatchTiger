package com.example.ngratzi.nicecatchtiger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class adminMain extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        ListView records = (ListView) findViewById(R.id.records);
        ArrayList<String> myStringArray1 = new ArrayList<String>();
        myStringArray1.add("Safety Issue: \nChemical Spill  \n4/17/2016");
        myStringArray1.add("Close Call: \nEquipment  \n4/17/2016");
        myStringArray1.add("Lesson Learned: \nWork Practive/Procedure  \n4/17/2016");
        myStringArray1.add("Lesson Learned: \nBiological  \n4/17/2016");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray1);
        records.setAdapter(adapter);
    }
}
