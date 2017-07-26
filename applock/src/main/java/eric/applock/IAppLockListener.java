package eric.applock;

import android.content.Intent;

/**
 * Created by wenjun.zhong on 2017/7/24.
 */

public interface IAppLockListener {
    void startAppLock(Intent intent, String pkg);
}
