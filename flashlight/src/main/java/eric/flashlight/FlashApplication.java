package eric.flashlight;

import android.app.Application;
import android.content.Context;

/**
*
 * Created by wenjun.zhong on 2017/8/7.
 */

public class FlashApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
    }

    public static Context getAppContext(){
        return appContext;
    }
}
