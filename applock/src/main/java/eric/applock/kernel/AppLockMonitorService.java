package eric.applock.kernel;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 *
 * Created by wenjun.zhong on 2017/7/26.
 */

public final class AppLockMonitorService extends Service implements IScreenStateListener {

    private AppLockMonitor appLockMonitor;
    private ScreenStateReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        appLockMonitor = AppLockMonitor.AppLockMonitorHolder.INSTANCE;
        return appLockMonitor;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (appLockMonitor != null) {
            appLockMonitor.interruptMonitor();
        }

        if (broadcastReceiver != null) {
            broadcastReceiver.listener = null;
            unregisterReceiver(broadcastReceiver);
        }
    }


    private void registerReceiver() {
        broadcastReceiver = new ScreenStateReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void screenOn() {
        if (appLockMonitor != null) {
            appLockMonitor.resumeMonitor();
        }
    }

    @Override
    public void screenOff() {
        if (appLockMonitor != null) {
            appLockMonitor.pauseMonitor();
            appLockMonitor.handlerScreenOff();
        }
    }

    static class ScreenStateReceiver extends BroadcastReceiver {
        private IScreenStateListener listener;

        public ScreenStateReceiver(IScreenStateListener listener) {
            this.listener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (listener == null) {
                return;
            }
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                listener.screenOff();
            } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                listener.screenOn();
            }
        }
    }
}
