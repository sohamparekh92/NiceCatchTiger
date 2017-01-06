package com.example.ngratzi.nicecatchtiger;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7800c9")));
        actionBar.setTitle("Nice Catch Tiger!");
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView introText = (TextView) findViewById(R.id.intro);
        TextView introContent = (TextView) findViewById(R.id.introContent);
        TextView definition = (TextView) findViewById(R.id.definition);
        TextView definitionContent = (TextView) findViewById(R.id.definition);
        TextView submitting = (TextView) findViewById(R.id.submitting);
        TextView submittingContent = (TextView) findViewById(R.id.submitting);
        TextView italic = (TextView) findViewById(R.id.italic);
        TextView italicContent = (TextView) findViewById(R.id.italic);
        TextView hotline = (TextView) findViewById(R.id.hotline);
        TextView hotlineContent = (TextView) findViewById(R.id.hotline);

        String intro = "<html><font color=\"#000000\"" +

                "<p>" +  "<i><b>Nice Catch Tiger!</b></i><br>" +
                "</p><br>" +
                "</font></html>";
        String introContentstring = "This App shares close calls and near misses in order to alert others and improve safety" +
                "awareness. If you see or experience a close call that if not \"caught\" in time could have resulted" +
                " in a serious injury, Please user this app to contact" +
                "Research Safety.";
        String definitionString = "<html><font color=\"#000000\"" +

                "<p align="+ "\"" +"left" + "\""+ ">" +  "<b>About:</b>MY data" +"</p>"+

                "</font></html>";
        String submittingString = "<html><font color=\"#000000\"" +

                "<p align="+ "\"" +"left" + "\""+ ">" +  "<b>About:</b>MY data" +"</p>"+

                "</font></html>";
        String italicString = "<html><font color=\"#000000\"" +

                "<p align="+ "\"" +"left" + "\""+ "><i>" +  "<b>About:</b>MY data" +"</i></p>"+

                "</font></html>";
        String hotlineString = "<html><font color=\"#000000\"" +

                "<p align="+ "\"" +"left" + "\""+ ">" +  "<b>About:</b>MY data" +"</p>"+

                "</font></html>";

        introText.setText(Html.fromHtml(intro));
        introContent.setText(introContentstring);
        definition.setText(Html.fromHtml(definitionString));
        submitting.setText(Html.fromHtml(submittingString));
        italic.setText(Html.fromHtml(italicString));
        hotline.setText(Html.fromHtml(hotlineString));
    }
}
