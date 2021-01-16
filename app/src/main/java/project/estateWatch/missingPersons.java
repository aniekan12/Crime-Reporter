package project.estateWatch;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class missingPersons extends AppCompatActivity {

    Uri imagePicker_uri;
    private static final int PICK_IMAGE = 100;
    ImageView missingPersonImage;
    Button imagePicker;

    EditText firstName, MiddleName, LastName, Date, LastSeen;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_persons);

        imageView = findViewById(R.id.missinPerson_Image);
        firstName = findViewById(R.id.FirstName);
        MiddleName = findViewById(R.id.MiddleName);
        LastName = findViewById(R.id.LastName);
        Date = findViewById(R.id.date_pick);
        LastSeen = findViewById(R.id.lastSeen);

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {
                ContentValues cv = new ContentValues();
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                cv.put("image", b);
                missingPersonImage = findViewById(R.id.missinPerson_Image);
                missingPersonImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

            //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
           // missingPersonImage.setImageBitmap(bitmap);
           // ByteArrayOutputStream ba = new ByteArrayOutputStream();
            //bitmap.compress( Bitmap.CompressFormat.PNG,90,ba );
        }



    public void myGallery(View v) {
        openGallery();
    }


    public void butnSubmiter(View View)
    {
        Send objSend = new Send();
        objSend.execute("");

    }
    private class Send extends AsyncTask<String, String, String>
    {



        String text1 = imageView.toString();
        String text2 = firstName.getText().toString();
        String text3 = MiddleName.getText().toString();
        String text4 = LastName.getText().toString();
        String text5 = Date.getText().toString();
        String text6 = LastSeen.getText().toString();



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


                    String query = "INSERT INTO missingpersonslog (Photograph, MissingPersonFirstName, MissingPersonMiddleName, MissingPersonLastName, Date, LastSeenLocation) VALUES('"+text1+"','"+text2+"','"+text3+"','"+text4+"','"+text5+"','"+text6+"')";
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
            Toast toast = Toast.makeText(getApplicationContext(),"We have received your Missing Person report, we'll get back to you.",Toast.LENGTH_LONG);
            toast.show();
        }




    }
}
