package project.estateWatch;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class NonRegisteredUsers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    private static final int VIDEO_CAPTURE_CODE = 1001;
    private static final int VIDEO_REQUEST = 1999;
    private static final int PERMISSION_CODE = 1000;
    Uri my_video_uri;

    VideoView myVideocaptureHolder;

    private static final int PICK_IMAGE = 101;
    ImageView missingPersonImage;
    Button imagePicker;
    Uri video_uri;
    VideoView mVideocaptureHold;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_registered_users);
        myDrawer = findViewById(R.id.drawer2);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        myToggle = new ActionBarDrawerToggle(this, myDrawer, toolbar, R.string.open, R.string.close);
        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.alert:
               Intent intent = new Intent(this, Alert.class);
               startActivity(intent);
                break;
            case R.id.panic:
                Intent intents = new Intent(this, panic.class);
                startActivity(intents);
                break;
        }
        myDrawer.closeDrawer(GravityCompat.START);
        return true;
    }






}

