package Helper;

import android.app.Application;
import android.content.Context;

/**
 * Created by HP on 2/15/2019.
 */

public class MainAPp extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"en"));
    }
}
