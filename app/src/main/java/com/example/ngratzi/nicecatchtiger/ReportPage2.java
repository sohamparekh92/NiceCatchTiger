package com.example.ngratzi.nicecatchtiger;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static android.R.attr.path;
import static android.R.attr.thumbnail;

public class ReportPage2 extends AppCompatActivity {

    int REQUEST_CAMERA = 0, SELECT_FILE = 1, PICTURE_RESULT = 2;
    Button btnSelect;
    ImageView ivImage;
    static TextView timeView;
    static TextView dateView;
    InstaAutoComplete departmentAutoComplete;
    InstaAutoComplete buildingAutoComplete;
    VideoView myVideo;
    String mCurrentPhotoPath;
    String mCurrentDirectory;
    EditText description, roomNumber;
    private URI delteableURI;
    SharedPreferences sharedPreferences;
    private boolean imageSet;
    private String myImageString;

    public static int building;

    private static String timeString;
    private static String dateString;
    private static String dateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page2_1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7800c9")));
        actionBar.setTitle("Nice Catch Tiger!");
        actionBar.setDisplayHomeAsUpEnabled(true);

        FormData.getInstance().addActivity(this);
        imageSet = false;
        sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(this);

        //Adding a Photo
        btnSelect = (Button) findViewById(R.id.imageButton);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.imageView);
        //End

        //Date and Time
        dateView = (TextView)findViewById(R.id.TextViewDate);
        timeView = (TextView)findViewById(R.id.TextViewTime);

        //Description and room number
        roomNumber = (EditText) findViewById(R.id.roomnumber);


        //Spinner for Building and Departments
        //departmentSpinner = (Spinner) findViewById(R.id.departmentOptions);

        //Populate Departments Array: content
        ArrayList<String> departmentsList = FormData.getInstance().getFormData("departments","departmentName");
        ArrayAdapter<String> departmentsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,departmentsList);
        departmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // departmentSpinner.setAdapter(departmentsAdapter);


        //Auto-Complete stuff

        departmentAutoComplete = (InstaAutoComplete) findViewById(R.id.departmentAutoComplete);
        departmentAutoComplete.setAdapter(departmentsAdapter);
        departmentAutoComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                departmentAutoComplete.showDropDown();
                return false;
            }
        });

        //Populate Building Array: content
        ArrayList<String> buildingsList = FormData.getInstance().getFormData("buildings","buildingName");
        ArrayAdapter<String> buildingsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,buildingsList);
        buildingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Auto Complete Stuff building
        buildingAutoComplete = (InstaAutoComplete) findViewById(R.id.buildingAutoComplete);
        buildingAutoComplete.setAdapter(buildingsAdapter);
        buildingAutoComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                buildingAutoComplete.showDropDown();
                return false;
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) (size.x*0.9);

        TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) buildingAutoComplete.getLayoutParams();
        layoutParams.width = width;
        buildingAutoComplete.setLayoutParams(layoutParams);

    }

    public void startPage3(View view) throws URISyntaxException {

        int error = 0;
        //data validation
        String descriptionString = "";
        String departmentString = "";
        String buildingString = "";
        String roomString = "";

        try{
            //departmentString = departmentSpinner.getSelectedItem().toString();
            departmentString = departmentAutoComplete.getText().toString();
            //buildingString = buildingSpinner.getSelectedItem().toString();
            buildingString = buildingAutoComplete.getText().toString();
            roomString = roomNumber.getText().toString();
        }
        catch (Exception e){
            error = 1;
        }

        if(departmentString.equals("") ||
                buildingString.equals("") ||
                roomString.equals("")) {

            error = 1;
        }
        if(timeView.getText().equals("Time:") || dateView.getText().equals("Date:")){
            error = 1;
        }

        if(error == 0) {
            Boolean deleteCheck = false;
            if(mCurrentPhotoPath!=null){
                delteableURI = new URI(mCurrentPhotoPath);
                File deletable = new File(delteableURI.getPath());
                deleteCheck = deletable.delete();
            }
            if (deleteCheck){
                Log.d("DeleteCheck","Deleted");
            }
            else{
                Log.d("DeleteCheck","Did not delete");
            }
            FormData.getInstance().addFormData("department",departmentString);
            FormData.getInstance().addFormData("room",roomString);
            FormData.getInstance().addFormData("buildingName",buildingString);
            FormData.getInstance().addFormData("incidentTime", dateString+" "+timeString);
            //timeView.setText("Time:");
            //dateView.setText("Date:");
            startActivity(new Intent(ReportPage2.this, ReportPage3.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Fill In All The Fields.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            ReportPage2.setDate(year,month,day);
        }
    }


    public static void setDate(int year, int month, int day){
        String monthString = "";
        switch (month){
            case 0: monthString = "Jan"; break;
            case 1: monthString = "Feb"; break;
            case 2: monthString = "Mar"; break;
            case 3: monthString = "Apr"; break;
            case 4: monthString = "May"; break;
            case 5: monthString = "Jun"; break;
            case 6: monthString = "Jul"; break;
            case 7: monthString = "Aug"; break;
            case 8: monthString = "Sep"; break;
            case 9: monthString = "Oct"; break;
            case 10: monthString = "Nov"; break;
            case 11: monthString = "Dec"; break;
        }
        dateView.setText("Date: "+monthString + " "+day+" "+year);
        month +=1;
        dateString = year+"-"+month+"-"+day;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        //newFragment.show(getSupportFragmentManager(), "timePicker");
        newFragment.show(getFragmentManager(),"timePicker");
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            ReportPage2.setTime(hourOfDay,minute);
        }
    }

    public static void setTime(int hour, int minute){
        String hourString = "";
        String minString = "";
        String period = "AM";
        int newHour = hour;
        if(hour>12){
            newHour = hour - 12;
            period = "PM";
        }
        if(hour<10){
            hourString = "0"+newHour;
        }
        else{
            hourString = ""+newHour;
        }

        if(minute<10){
            minString = "0"+minute;
        }
        else {
            minString= ""+minute;
        }
        timeView.setText("Time: "+ hourString + ":" + minString +" "+ period);
        /*if(hour>12) {
            timeView.setText("Time: "+ (hour - 12) + ":" + minString + " PM");
        }
        else{
            timeView.setText("Time: "+ hour + ":" + minString + " AM");
        }*/
        timeString = hour+":"+minute+":00";
    }



    /*
    Adding Pictures
     */
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ReportPage2.this);
        builder.setTitle("Add Photo!");
        AlertDialog.Builder cancel = builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                imageSet = true;
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(intent, PICTURE_RESULT);
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("Exception","No File");
                    }
                    //Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),"com.example.ngratzi.nicecatchtiger",photoFile);
                    Uri mImageUri = Uri.fromFile(photoFile);
                    if(mImageUri==null){
                        Log.d("Image URI Status", "NULL");
                    }
                    else{
                        Log.d("Image URI Status", "NOT NULL");
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, PICTURE_RESULT);

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    imageSet = false;
                }
            }
        });
        builder.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        mCurrentDirectory = storageDir.getAbsolutePath();
        Log.d("Photo Path", mCurrentPhotoPath);
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                selectFromGallery(data);
            else if (resultCode != RESULT_CANCELED) {
                    if(requestCode == PICTURE_RESULT) {
                        try {
                            Toast.makeText(this,"Loading Image",Toast.LENGTH_LONG);
                            onCaptureImageResult(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] decodedString = null;
                Bitmap decodedByte = null;
                while (decodedByte==null) {
                    try {
                        Thread.sleep(100);
                         decodedString = Base64.decode(myImageString, Base64.DEFAULT);
                         decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                        break;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                final Bitmap finalDecodedByte = decodedByte;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivImage.setImageBitmap(finalDecodedByte);
                    }
                });
            }
        }).start();

    }

    private void onCaptureImageResult(Intent data) throws IOException, InterruptedException {

        final String originalPath = mCurrentPhotoPath;
        mCurrentPhotoPath = "file:"+mCurrentPhotoPath;

        final Bitmap[] originalBitmap = new Bitmap[1];
        Thread decode = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while(originalBitmap[0]==null) {
                        originalBitmap[0] = BitmapFactory.decodeFile(originalPath);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        decode.run();
        Bitmap bitmap = originalBitmap[0];
        try {
            if (bitmap.getWidth() > bitmap.getHeight()) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 960, 640, false);
            } else {
                bitmap = Bitmap.createScaledBitmap(bitmap, 640, 960, false);
            }

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String encodedImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
            FormData.getInstance().addFormData("encodedImage", encodedImage);
            myImageString = encodedImage;
            FormData.setPictureTaken(true);
            //ivImage.setImageBitmap(bitmap);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this,"Could not upload Image",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Please try again",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFromGallery(Intent data){
        Uri targetUri = data.getData();
        //textTargetUri.setText(targetUri.toString());
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));

            Bitmap newImage = bitmap;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            if(newImage == null){
                Log.i("Bitmap Check", "Null");
            }
            else{
                Log.i("Bitmap Check", "Not Null");
            }

            //newImage = scaleBitmap(newImage, 1920,1080);
            newImage.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            myImageString = encodedImage;
            FormData.getInstance().addFormData("encodedImage",encodedImage);
            //FormData.getInstance().addFormData("imageData", Arrays.toString(bytes.toByteArray()));
            FormData.getInstance().addFormData("imageData", byteArrayOutputStream.toByteArray().toString());
            FormData.setImageBytes(byteArrayOutputStream.toByteArray());
            FormData.setPictureTaken(true);


        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {
        if(bitmapToScale == null)
            return null;
//get the original width and height
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();
// create a matrix for the manipulation
        Matrix matrix = new Matrix();

// resize the bit map
        matrix.postScale(newWidth / width, newHeight / height);

// recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Save Image
        if(imageSet) {
            sharedPreferences.edit().putBoolean("imageCheck",true).commit();
            sharedPreferences.edit().putString("imageData", myImageString).commit();
        }

        //Save Department
        sharedPreferences.edit().putString("department",departmentAutoComplete.getText().toString()).apply();

        //Save Building
        sharedPreferences.edit().putString("building", buildingAutoComplete.getText().toString()).apply();

        //Save Room No.
        sharedPreferences.edit().putString("room",roomNumber.getText().toString()).apply();

        //Save Date
        sharedPreferences.edit().putString("date", dateString).apply();

        //Save Time
        sharedPreferences.edit().putString("time",timeString).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Restore Image
        if(sharedPreferences.getBoolean("imageCheck",false)) {
            byte[] decodedString = Base64.decode(sharedPreferences.getString("imageData", ""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivImage.setImageBitmap(decodedByte);
        }

        //Restore Department
        departmentAutoComplete.setText(sharedPreferences.getString("department",""));

        //Restore Building
        buildingAutoComplete.setText(sharedPreferences.getString("building",""));

        //Restore Room No.
        roomNumber.setText(sharedPreferences.getString("room",""));

        //Restore Date
        String date = sharedPreferences.getString("date","");
        int month;
        int day;
        int year;
        if(!date.equals("")){
            String [] dateArray = date.split("-");
            year = Integer.parseInt(dateArray[0]);
            day = Integer.parseInt(dateArray[2]);
            month = Integer.parseInt(dateArray[1]);
            setDate(year,month-1,day);
        }

        //Restore Time
        String time = sharedPreferences.getString("time","");
        int hours;
        int mins;
        if(!time.equals("")) {
            String[] timeArray = time.split(":");
            hours = Integer.parseInt(timeArray[0]);
            mins = Integer.parseInt(timeArray[1]);
            setTime(hours,mins);
        }

    }
}
