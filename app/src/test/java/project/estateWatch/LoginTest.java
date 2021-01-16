package project.estateWatch;


import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;



import static org.junit.Assert.*;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class LoginTest {
private Login activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Login.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView appNameTextView = (TextView) activity.findViewById(R.id.nrUsers);
        assertTrue("Don't want to be registered? Click here!".equals(appNameTextView.getText().toString()));
    }
}