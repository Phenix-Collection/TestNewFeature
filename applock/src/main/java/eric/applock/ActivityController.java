package eric.applock;

import android.app.IActivityController;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author htoall
 * @Description:
 * @date 2017/7/21 下午2:07
 * @copyright TCL-MIE
 */
public class ActivityController extends IActivityController.Stub{

    private IAppLockListener listener;

    public ActivityController(IAppLockListener listener){
        this.listener = listener;
    }

    @Override
    public boolean activityStarting(Intent intent, String pkg) throws RemoteException {
        boolean isNeedLock = NeedAppLock.isNeedLock(pkg);
        Log.d("0721", " activityStarting -- pkg : " + pkg  + "; isNeedLock: " + isNeedLock + " intent :" + intent);
        assert listener != null;
        boolean result = true;
        if(isNeedLock && listener != null) {
            result = false;
            listener.startAppLock(intent, pkg);
        }
        return result;
    }

    @Override
    public boolean activityResuming(String pkg) throws RemoteException {
        Log.d("0721", " activityResuming -- pkg : " + pkg);
        return true;
    }

    @Override
    public boolean appCrashed(String processName, int pid, String shortMsg, String longMsg, long timeMillis, String stackTrace) throws RemoteException {
        return false;
    }

    @Override
    public int appEarlyNotResponding(String processName, int pid, String annotation) throws RemoteException {
        return 0;
    }

    @Override
    public int appNotResponding(String processName, int pid, String processStats) throws RemoteException {
        return 0;
    }

    @Override
    public int systemNotResponding(String msg) throws RemoteException {
        return 0;
    }
}
