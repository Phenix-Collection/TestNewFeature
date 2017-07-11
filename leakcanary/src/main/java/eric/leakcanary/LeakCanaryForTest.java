package eric.leakcanary;

import android.app.Application;
import android.support.annotation.Keep;
import android.util.Log;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.leakcanary.internal.DisplayLeakActivity;
import com.squareup.leakcanary.internal.LeakCanaryInternals;

/**
 *
 * Created by wenjun.zhong on 2017/7/5.
 */

@Keep
public final class LeakCanaryForTest {
    private  static  String sAppPakcageName = "";
    private static RefWatcher sWatcher;

    @android.support.annotation.Keep
    public static void install(Application application){
        assert application != null;
        if(LeakCanary.isInAnalyzerProcess(application)){
            return;
        }
        Log.w("installLeakCanary", "install in " + application.getPackageName());
        sWatcher = LeakCanary.refWatcher(application)
                .listenerServiceClass(LeakUploadServer.class)
                .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
                .buildAndInstall();

        // disable DisplayLeakActivity
        LeakCanaryInternals.setEnabled(application, DisplayLeakActivity.class, false);
    }

    public static void watch(Object object){
        if(sWatcher != null){
            sWatcher.watch(object);
        }else{
            Log.e("leak watch", "sWatch == null");
        }
    }
}
