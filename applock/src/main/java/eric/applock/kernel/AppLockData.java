package eric.applock.kernel;

import android.util.Log;

import eric.applock.BuildConfig;

/**
 * 上锁应用数据 Created by wenjun.zhong on 2017/7/26.
 */

public final class AppLockData {
    private static final String TAG = "AppLockMonitor";
    public String packageName = "";
    private AppLockStrategy lockStrategy = AppLockStrategy.LOCK_SCREEN;
    private boolean isLockScreen = true;
    private boolean isExitApp = true;
    private boolean isUnlockSuccess = false;
    private long exitAppTime = 0L;

    public AppLockData(String packageName, AppLockStrategy lockStrategy) {
        this.packageName = packageName;
        this.lockStrategy = lockStrategy;
    }

    boolean isNeedLock() {
        if (isLockScreen) {
            isUnlockSuccess = false;
            return true;
        }
        boolean isNeeLock = true;
        switch (lockStrategy) {
            case LOCK_SCREEN:
                isNeeLock = false;
                break;
            case EXIT_APP:
                isNeeLock = isExitApp;
                break;
            case EXIT_APP_MINUTE_1:
                isNeeLock = isExitApp && (System.currentTimeMillis() - exitAppTime > AppLockStrategy.EXIT_APP_MINUTE_1.time);
                break;
            default:
                isNeeLock = false;
                break;
        }
        if (isNeeLock) {
            isUnlockSuccess = false;
        }
        return isNeeLock;
    }

    // 应用锁解锁成功
    public void unlockApp() {
        isUnlockSuccess = true;
        isLockScreen = false;
        isExitApp = false;
        exitAppTime = 0l;
    }

    // 手机锁屏处理
    void lockScreen() {
        isLockScreen = true;
        isExitApp = true;
        isUnlockSuccess = false;
        exitAppTime = 0;
    }

    // 应用退出处理
    void exitApp() {
        isExitApp = true;
        switch (lockStrategy) {
            case EXIT_APP_MINUTE_1:
                if (isUnlockSuccess) {
                    exitAppTime = System.currentTimeMillis();
                }
                break;
        }

        if (BuildConfig.DEBUG) {
            Log.w(TAG, packageName + "  exit, isUnlockSuccess: " + isUnlockSuccess);
        }
    }


    @Override
    public String toString() {
        return "AppLockData{" + "packageName='" + packageName + '\'' + ", lockStrategy=" + lockStrategy
                + ", isLockScreen=" + isLockScreen + ", isExitApp=" + isExitApp + ", isUnlockSuccess="
                + isUnlockSuccess + ", exitAppTime=" + exitAppTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppLockData that = (AppLockData) o;

        return packageName.equals(that.packageName);

    }

    @Override
    public int hashCode() {
        return packageName.hashCode();
    }
}
