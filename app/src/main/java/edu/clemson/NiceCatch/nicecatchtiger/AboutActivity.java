package edu.clemson.NiceCatch.nicecatchtiger;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.widget.TableRow;
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

                "<p>" +  "<i><b>Nice Catch Tiger!</b></i><br><br>" +
                "This App shares close calls and near misses in order to alert others and improve safety " +
                "awareness. If you see or experience a close call that if not \"caught\" in time could have resulted" +
                " in a serious injury, please use this app to contact Research Safety."+
                "</p>" +
                "</font></html>";
        String definitionString = "<html><font color=\"#000000\"" +

                "<p>" +  "<b>Definition of a Near Miss</b><br><br>" +
                "An incident or an event that could have resulted in injuries or other adverse consequences but fortunately did not."+
                "</p><br>" +
                "</font></html>";
        String submittingString = "<html><font color=\"#000000\"" +

                "<p>" +  "<b>Once submitted, Research Safety will:</b><br><br></p>" +
                "<ul>" +
                "<li>  • Review and follow up submittals </li><br><br>" +
                "<li>  • Share information and \"lesson learned\" with researchers across campus (without identifying specific" +
                "lab or staff when requested) </li><br><br>" +
                "<li>  • Appreciate your submittal and commitment to safety </li><br><br>" +
                "</ul>"+
                "</font></html>";

        String italicString = "<html><font color=\"#000000\"" +

                "<p>" +
                "<i>"+
                "Please share your experiences with us so that we may continue to improve the safety culture at Clemson University."+
                "</i>"+
                "</p><br>" +
                "</font></html>";
        String hotlineString = "<html><font color=\"#000000\"" +

                "<p align="+ "\"" +"left" + "\""+ ">" +  "If you have a serious safety" +
                " or ethical concern but do not feel comfortable sharing your identity," +
                " please use the confidential <a href=\"http://www.clemson.edu/administration/internalaudit/ethicsline.html\">hotline</a>.</p>"+
                "<br>"+
                "<br>" +
                "<br>" +
                " From the Office of Research Safety & the Office of Research Compliance\n" +
                "<br>" +
                "<br>" +
                "<a href=\"http://www.clemson.edu/research/compliance/\">http://www.clemson.edu/research/compliance/</a>.</p>"+
                "<br>" +
                "<a href=\"http://www.clemson.edu/research/safety/\">http://www.clemson.edu/research/safety/</a>.</p>"+

                "</font></html>";

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) (size.x*0.9);

        android.widget.TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) introText.getLayoutParams();
        layoutParams.width = width;
        introText.setText(Html.fromHtml(intro));
        introText.setLayoutParams(layoutParams);

        layoutParams = (TableRow.LayoutParams) definition.getLayoutParams();
        layoutParams.width = width;
        definition.setText(Html.fromHtml(definitionString));
        definition.setLayoutParams(layoutParams);

        layoutParams = (TableRow.LayoutParams) submitting.getLayoutParams();
        layoutParams.width = width;
        submitting.setText(Html.fromHtml(submittingString));
        submitting.setLayoutParams(layoutParams);

        layoutParams = (TableRow.LayoutParams) italic.getLayoutParams();
        layoutParams.width = width;
        italic.setText(Html.fromHtml(italicString));
        italic.setLayoutParams(layoutParams);

        layoutParams = (TableRow.LayoutParams) hotline.getLayoutParams();
        layoutParams.width = width;
        hotline.setText(Html.fromHtml(hotlineString));
        hotline.setLayoutParams(layoutParams);
        hotline.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
