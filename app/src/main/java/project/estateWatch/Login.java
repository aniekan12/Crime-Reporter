package project.estateWatch;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button btnLogin;
    myDatabaseHelper mydb;

    boolean VISIBLE_PASSWORD = false;  //declare as global variable befor onCreate()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button myjoinusbutton = (Button) findViewById(R.id.joinus);
        myjoinusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });


        //mydb = new myDatabaseHelper(this);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.thispassword);



    }


    public String validate(String userName, String password)
    {
        if(userName.equals("user") && password.equals("user"))
            return "Login was successful";
        else
            return "Invalid login!";
    }

    public void butn(View View)
    {
        Send objSend = new Send();
        objSend.execute("");

    }

    public void learn(View view) {
        if(view.getId()==R.id.learn){
            startActivity(new Intent(Login.this,LearnMore.class));
        }
    }

    private class Send extends AsyncTask<String, String, String>
    {

        String text1 = editTextEmail.getText().toString();
        String text2 = editTextPassword.getText().toString();



        @Override
        protected void onPreExecute()
        {
            Toast toast = Toast.makeText(getApplicationContext(),"We're working on your Request",Toast.LENGTH_LONG);
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


            String msg ="";



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
                    Toast toast = Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_LONG);
                    toast.show();

                }
                else if(conn != null)
                {


                    String query2 = "select * from user where email='" + text1 + "' and password='" + text2 + "'";
                    Statement stmt = conn.createStatement();


                    ResultSet ss = stmt.executeQuery(query2);

                    if(ss.next())
                    {
                        Intent i = new Intent(Login.this, Home.class);
                        i.putExtra("Email", text1);
                        startActivity(i);

                    }
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
            Toast toast = Toast.makeText(getApplicationContext(),"Welcome to the Watchman",Toast.LENGTH_LONG);
            toast.show();
        }

        //textView.setText("Welcome " + username);

    }




    public void nrPhase(View view) {
        if(view.getId()==R.id.nrUsers){
          startActivity(new Intent(Login.this,NonRegisteredUsers.class));
        }
    }



}
