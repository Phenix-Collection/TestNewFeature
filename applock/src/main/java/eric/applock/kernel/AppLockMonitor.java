package eric.applock.kernel;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import eric.applock.BuildConfig;

/**
 *
 * Created by wenjun.zhong on 2017/7/26.
 */

final class AppLockMonitor extends Binder {
    private static final String TAG = "AppLockMonitor";
    private static final int SLEEP_TIME = 300;
    private final Object lock = new Object();
    private Thread mThread;
    private String mPrePackageName = "";
    private boolean isInterrupt;
    private volatile boolean isPause;
    private final Context context;

    private HashMap<String, AppLockData> appLockDataHashMap = new HashMap<>(10);

    private AppLockMonitor(Context context) {
        this.context = context.getApplicationContext();
        if (BuildConfig.DEBUG) {
            appLockDataHashMap.put("com.tencent.mobileqq", new AppLockData("com.tencent.mobileqq",
                    AppLockStrategy.LOCK_SCREEN));
            appLockDataHashMap.put("com.sohu.newsclient", new AppLockData("com.sohu.newsclient",
                    AppLockStrategy.EXIT_APP));
            appLockDataHashMap.put("com.facebook.katana", new AppLockData("com.facebook.katana",
                    AppLockStrategy.EXIT_APP_MINUTE_1));
            appLockDataHashMap.put("com.twitter.android", new AppLockData("com.twitter.android",
                    AppLockStrategy.EXIT_APP));
            appLockDataHashMap.put("com.android.dialer", new AppLockData("com.android.dialer",
                    AppLockStrategy.EXIT_APP_MINUTE_1));
        }
        startAppMonitor();
    }

    static final class AppLockMonitorHolder {
        final static AppLockMonitor INSTANCE = new AppLockMonitor(null);
    }


    private void startAppMonitor() {
        isInterrupt = false;
        isPause = false;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String currentApp;
                AppLockData currentAppLockData;
                AppLockData preAppLockData;
                while (!isInterrupt) {
                    if (isPause) {
                        try {
                            synchronized (mThread) {
                                mThread.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.w(TAG, e.toString());
                        }
                    }

                    currentApp = getForegroundApp();
                    if (BuildConfig.DEBUG) {
                        Log.w(TAG, "currentApp: " + currentApp);
                    }

                    if (!TextUtils.isEmpty(currentApp) && !currentApp.equals(mPrePackageName)) {
                        synchronized (lock) {
                            currentAppLockData = appLockDataHashMap.get(currentApp);
                            preAppLockData = appLockDataHashMap.get(mPrePackageName);
                        }

                        if (currentAppLockData != null && currentAppLockData.isNeedLock()) {
                            needShowAppLock(currentApp);
                        }

                        if (preAppLockData != null) {
                            preAppLockData.exitApp();
                        }
                        mPrePackageName = currentApp;
                    }
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }


    @Nullable
    private String getForegroundApp() {
        String currentApp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (!mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        return currentApp;
    }

    @Nullable
    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
    private String getForegroundAppAbove22() throws Exception {
        String topPackageName = "";
        UsageStatsManager usage = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        UsageEvents.Event event = new UsageEvents.Event();
        UsageEvents usageEvents = usage.queryEvents(time - 1000 * 60 * 60 * 5, time);
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                topPackageName = event.getPackageName();
            }
        }
        return topPackageName;
    }

    @Nullable
    private String getForegoundAppBelow22() {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                RunningTaskInfo runningtaskinfo = list.get(0);
                if (runningtaskinfo == null) {
                    return null;
                }
                ComponentName activity = runningtaskinfo.topActivity;
                if (activity == null) {
                    return null;
                }
                return activity.getPackageName();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void needShowAppLock(String packageName) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, packageName + "  need show app lock");
        }
    }


    void interruptMonitor() {
        mThread.interrupt();
        isInterrupt = true;
    }

    /**
     * 挂起（例如：锁屏暗了就挂起）
     */
    void pauseMonitor() {
        isPause = true;
        if (BuildConfig.DEBUG) {
            Log.w(TAG, "pauseMonitor");
        }
    }

    /**
     * 恢复（例如：屏幕亮了就恢复）
     */
    void resumeMonitor() {
        isPause = false;
        synchronized (mThread) {
            mThread.notify();
        }
        if (BuildConfig.DEBUG) {
            Log.w(TAG, "resumeMonitor");
        }
    }

    /**
     * 处理屏幕暗
     */
    void handlerScreenOff() {
        synchronized (lock) {
            for (Map.Entry<String, AppLockData> entry : appLockDataHashMap.entrySet()) {
                entry.getValue().lockScreen();
            }
        }
    }
}
