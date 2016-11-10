package com.example.ngratzi.nicecatchtiger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ReportPage2 extends AppCompatActivity {

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect;
    ImageView ivImage;
    VideoView myVideo;
    EditText description, roomNumber;
    Spinner departmentSpinner, buildingSpinner;
    ArrayList<Integer> departmentIDs, buildingIDs;


    public static String Description;
    public static String RNumber;
    public static int department;
    public static int building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page2_1);

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

        //Description and room number
        description = (EditText) findViewById(R.id.description);
        roomNumber = (EditText) findViewById(R.id.roomnumber);

        //Spinner for Building and Departments
        departmentSpinner = (Spinner) findViewById(R.id.departmentOptions);
        buildingSpinner = (Spinner) findViewById(R.id.buildingOptions);

        //Populate Departments Array: content
        ArrayList<String> departmentsList = FormData.getInstance().getFormData("departments","departmentName");
        ArrayAdapter<String> departmentsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,departmentsList);
        departmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentsAdapter);

        //Populate Building Array: content
        ArrayList<String> buildingsList = FormData.getInstance().getFormData("buildings","buildingName");
        ArrayAdapter<String> buildingsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,buildingsList);
        buildingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingsAdapter);




    }

    public void startPage3(View view) {

        int error = 0;
        //data validation
        String descriptionString = "";
        String departmentString = "";
        String buildingString = "";
        String roomString = "";

        try{
            descriptionString = description.getText().toString();
            departmentString = departmentSpinner.getSelectedItem().toString();
            buildingString = buildingSpinner.getSelectedItem().toString();
            roomString = roomNumber.getText().toString();
        }
        catch (Exception e){
            error = 1;
        }

        if(departmentString.equals("") ||
                descriptionString.equals("") ||
                buildingString.equals("") ||
                roomString.equals("")) {

            error = 1;
        }

        if(error == 0) {
            FormData.getInstance().addFormData("department",departmentString);
            FormData.getInstance().addFormData("description",descriptionString);
            FormData.getInstance().addFormData("room",roomString);
            FormData.getInstance().addFormData("buildingName",buildingString);
            startActivity(new Intent(ReportPage2.this, ReportPage3.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Fill In All The Fields.", Toast.LENGTH_SHORT).show();
        }
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;
    Intent takeVideoIntent;

    public void dispatchTakeVideoIntent() {
        takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }



    /*
    Adding Pictures
     */
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ReportPage2.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = takeVideoIntent.getData();
            myVideo.setVideoURI(videoUri);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap newImage = thumbnail;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        newImage.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        FormData.getInstance().addFormData("encodedImage",encodedImage);
        //FormData.getInstance().addFormData("imageData", Arrays.toString(bytes.toByteArray()));
        FormData.getInstance().addFormData("imageData", bytes.toByteArray().toString());
        FormData.setImageBytes(bytes.toByteArray());
        Log.i("imageData", bytes.toByteArray().toString());
        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);
    }
}
