package project.estateWatch;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editTextEmail, editTextPhone, editTextAddress, editTextCPassword;
    TextView textView;
    Button BtnSignUp;
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.reg_username);
        textView = (TextView) findViewById(R.id.c);
        editTextPassword = findViewById(R.id.reg_password);
        editTextEmail = findViewById(R.id.reg_email);
        editTextPhone = findViewById(R.id.reg_phone);
        editTextAddress = findViewById(R.id.reg_address);
        editTextCPassword = findViewById(R.id.reg_confirmpassword);
        BtnSignUp = findViewById(R.id.sign_up);
    }


    private boolean validate() {
        boolean temp=true;
        String addy = "ShelterView Estate";
        String address = editTextAddress.getText().toString();
        String checkemail = editTextEmail.getText().toString();
        String pass=editTextPassword.getText().toString();
        String cpass=editTextCPassword.getText().toString();
        if(!EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()){
            Toast.makeText(Register.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            temp=false;
        }

        else if (!address.equals(addy)) {
            Toast.makeText(Register.this,"Sorry, you can't be registered on this application",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(!pass.equals(cpass)){
            Toast.makeText(Register.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;

    }


    public void btnConn(View View)
    {
        if (validate()) {
            Send objSend = new Send();
            objSend.execute("");
        }

    }

    private class Send extends AsyncTask<String, String, String>
    {
        String msg = "";
        String text1 = editTextUsername.getText().toString();
        String text2 = editTextPassword.getText().toString();
        String text3 = editTextEmail.getText().toString().trim();
        String text4 = editTextPhone.getText().toString();
        String text7 = editTextCPassword.getText().toString();
        String text5 = editTextAddress.getText().toString();


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
                else
                {
                    String query = "INSERT INTO user (Name, Password,  PhoneNumber, email, Address) VALUES('"+text1+"','"+text2+"','"+text4+"', '"+text3+"','"+text5+"')";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    msg = "Registered succesfull";
                }
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
            Toast toast = Toast.makeText(getApplicationContext(),"Registration is Successful",Toast.LENGTH_LONG);
            toast.show();

            startActivity(new Intent(Register.this,Login.class));
            }

    }

}
