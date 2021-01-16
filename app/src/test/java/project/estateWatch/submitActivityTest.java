package project.estateWatch;

import android.os.Build;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
//@Config(constants = BuildConfig.class, sdk = 21, packageName = "project.estateWatch")
//@RunWith(RobolectricGradleTestRunner.class)

public class submitActivityTest {
     submitActivity submitActivity;
    @Test
    public void butnSubmit() {

        View view = submitActivity.findViewById(R.id.sendReport);
        assertNotNull(view);
    }
}