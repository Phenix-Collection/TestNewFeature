package eric.applock;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements IAppLockListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityController(true);
        Log.w(NeedAppLock.TAG, "MainActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(NeedAppLock.TAG, "MainActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(NeedAppLock.TAG, "MainActivity onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(NeedAppLock.TAG, "MainActivity onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(NeedAppLock.TAG, "MainActivity onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(NeedAppLock.TAG, "MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(NeedAppLock.TAG, "MainActivity onPause");
    }

    public void resetLockState(View view){
        NeedAppLock.resetLockState();
    }


    @RequiresPermission("android.permission.SET_ACTIVITY_WATCHER")
    private void setActivityController(boolean isStartListener) {
        try {
            Log.d("0721", " setActivityController: 1");
            Class<?> cActivityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Method mGetDefault = cActivityManagerNative.getMethod("getDefault");
            Object oActivityManagerNative = mGetDefault.invoke(null);

            // Class<?> i = Class.forName("android.app.IActivityController$Stub");

            if (Build.VERSION.SDK_INT > 23) {
                Method mSetActivityController = cActivityManagerNative.getMethod("setActivityController",
                        Class.forName("android.app.IActivityController"), boolean.class);
                mSetActivityController.invoke(oActivityManagerNative,
                        isStartListener ? new ActivityController(this) : null, false);
            } else {
                Method mSetActivityController = cActivityManagerNative.getMethod("setActivityController",
                        Class.forName("android.app.IActivityController"));
                mSetActivityController.invoke(oActivityManagerNative,
                        isStartListener ? new ActivityController(this) : null);
            }
            Log.d("0721", " setActivityController: 2");
            /*
             * Method mSetActivityController =
             * cActivityManagerNative.getMethod("setActivityController",
             * Class.forName("android.app.IActivityController"));
             * mSetActivityController.invoke(oActivityManagerNative, isStartListener ? new
             * ActivityController(mContext) : null);
             */

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d("0721", " ClassNotFoundException: " + e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.d("0721", " NoSuchMethodException: " + e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.d("0721", " IllegalArgumentException: " + e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d("0721", " IllegalAccessException: " + e);
            Log.d("0721", Log.getStackTraceString(e));
        } catch (InvocationTargetException e) {
            Log.d("0721", " InvocationTargetException: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("0721", " Exception: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void startAppLock(Intent intent, String pkg) {
        Intent lockIntent = new Intent(getApplicationContext(), AppLockActivity.class);
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        lockIntent.putExtra("packageName", pkg);
        lockIntent.putExtra("intentStr", intent);
        startActivity(lockIntent);
    }
}
