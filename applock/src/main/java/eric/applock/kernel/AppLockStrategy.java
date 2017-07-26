package eric.applock.kernel;

/**
 * 应用锁策略(什么时机应用再次上锁) Created by wenjun.zhong on 2017/7/26.
 */

enum AppLockStrategy {
    /**
     * 锁屏后 应用上锁
     */
    LOCK_SCREEN(0),

    /**
     * 退出应用后上锁
     */
    EXIT_APP(0),

    /**
     * 退出应用1分钟后上锁
     */
    EXIT_APP_MINUTE_1(60 * 1000);

    public long time;

    AppLockStrategy(long time) {
        this.time = time;
    }

}
