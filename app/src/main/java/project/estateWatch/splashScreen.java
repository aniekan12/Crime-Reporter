package project.estateWatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class splashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //setContentView(R.layout);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(splashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }


    }
