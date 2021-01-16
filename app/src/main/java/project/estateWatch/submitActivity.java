package project.estateWatch;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class submitActivity extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int VIDEO_CAPTURE_CODE = 1001;
    Uri image_uri;
    Uri video_uri;
    ImageView mcaptureHolder;
    VideoView mVideocaptureHolder;
    TextView textView;
    private static final int VIDEO_REQUEST = 1999;
    public static final int CAMERA_REQUEST = 101;
    private static final int PERMISSION_CODE = 1000;

    EditText reportName, reportDesc, date, report, crimeType, crimeLocation;
    ImageView imageCapture;
    VideoView VideoCapture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        reportName = findViewById(R.id.report_names);
        reportDesc = findViewById(R.id.report_description);
        date = findViewById(R.id.date_picker);
        report = findViewById(R.id.write_report);
        crimeType = findViewById(R.id.crime_type);
        crimeLocation = findViewById(R.id.crime_location);
        imageCapture = findViewById(R.id.captureHold);
        VideoCapture = findViewById(R.id.VideoCaptureHold);
    }

    public void butnSubmit(View View)
    {
        Send objSend = new Send();
        objSend.execute("");

    }

    public void onClick(View view) {
        //if system os is >= marshmallow, request runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                //REQUEST PERMISSION IF NOT ENABLED
                String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //SHOW POPUP TO REQUEST PERMISSION
                requestPermissions(permission,PERMISSION_CODE);
            }
            else {
                //permisson already granted
                openCamera();
            }
        }
        else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        //Camera intent
        Intent myCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        myCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(myCameraIntent,IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // method that is called when user presses allow or deny
        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    openCamera();
                }
                else{
                    //permission not granted
                    Toast.makeText(this,"Permission denied...",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    public void VideoonClick(View view) {
        //ASK FOR PERMISSION IF OS IS GREATER THAN MARSHMALLOW
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {

                //PERMISSION IS NOT ENABLED, REQUEST IT
                String [] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //SHOW POPUP TO REQUEST PERMISSION
                requestPermissions(permission,PERMISSION_CODE);
            }
            else {
                //permission granted
                openVideo();
            }
        }
        else {
            //system os < marshmallow
            openVideo();
        }
    }

    private void openVideo(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Video");
        video_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera Intent
        Intent myVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        myVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,video_uri);
        myVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(myVideoIntent, VIDEO_CAPTURE_CODE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //called when image captured from camera
        if (resultCode == RESULT_OK){
            mcaptureHolder = findViewById(R.id.captureHold);
            mcaptureHolder.setImageURI(image_uri);

        /*    missingPersonImage = findViewById(R.id.missinPerson_Image);
            imagePicker_uri = data.getData();
            missingPersonImage.setImageURI(imagePicker_uri);*/
        }
        if (resultCode == RESULT_OK) {
            mVideocaptureHolder = findViewById(R.id.VideoCaptureHold);
            video_uri = data.getData();
            mVideocaptureHolder.setVideoURI(video_uri);
            mVideocaptureHolder.setMediaController(new MediaController(this));
            mVideocaptureHolder.requestFocus();
            mVideocaptureHolder.start();

        }

      /*  if (requestCode == PICK_IMAGE && resultCode == RESULT_OK)  {

        }*/
    }



private class Send extends AsyncTask<String, String, String>
    {



        String text1 = reportName.getText().toString();
        String text2 = reportDesc.getText().toString();
        String text3 = VideoCapture.toString();
        String text4 = imageCapture.toString();
        String text5 = crimeLocation.getText().toString();
        String text6 = date.getText().toString();
        String text7 = crimeType.getText().toString();



        @Override
        protected void onPreExecute()
        {
            Toast toast = Toast.makeText(getApplicationContext(),"We're working on your request",Toast.LENGTH_LONG);
            toast.show();

        }

        @Override
        protected String doInBackground(String... strings)
        {

            // Initialize connection variables.
            String host = "watchman.mysql.database.azure.com";
            String database = "watchman";
            String user = "myadmin@watchman";
            String password = "Aniekana12";

            String msg = "";



            try
            {
                String url = String.format("jdbc:mysql://%s/%s", host, database);

                // Set connection properties.
                Properties properties = new Properties();
                properties.setProperty("user", user);
                properties.setProperty("password", password);
                properties.setProperty("useSSL", "true");
                properties.setProperty("verifyServerCertificate", "true");
                properties.setProperty("requireSSL", "false");

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, properties);
                if(conn == null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Oops!, something went wrong",Toast.LENGTH_LONG);
                    toast.show();

                }
                else if(conn != null)
                {


                    String query = "INSERT INTO crimereport (ReportName, ReportDescription, VideoEvidence, ImageEvidence, crimeLocation, Date, crimeType) VALUES('"+text1+"','"+text2+"','"+text3+"','"+text4+"','"+text5+"','"+text6+"','"+text7+"')";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);



                }
                else

                    conn.close();
            }
            catch (Exception e)
            {
                msg = "Connection goes wrong";
                e.printStackTrace();
            }
            return msg;

        }

        @Override
        protected void onPostExecute(String msg)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"We have received your report, we'll get back to you.",Toast.LENGTH_LONG);
            toast.show();
        }




    }
}
