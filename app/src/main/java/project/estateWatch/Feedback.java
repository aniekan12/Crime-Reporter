package project.estateWatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Feedback extends AppCompatActivity {

    EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback = findViewById(R.id.feedbackmessage);

    }

    public void butnfeed(View View)
    {
        Send objSend = new Send();
        objSend.execute("");

    }

    private class Send extends AsyncTask<String, String, String>
    {

        String text1 = feedback.getText().toString();



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


                    String query = "INSERT INTO feedback (Feedback_Message) VALUES('"+text1+"')";
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
            Toast toast = Toast.makeText(getApplicationContext(),"Your opinion is valid.",Toast.LENGTH_LONG);
            toast.show();
        }

        //textView.setText("Welcome " + username);

    }

}
