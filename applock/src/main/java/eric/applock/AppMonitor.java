package eric.applock;

import android.os.Binder;

/**
*
 * Created by wenjun.zhong on 2017/7/25.
 */

public final class AppMonitor extends Binder{
    private static final int SLEEP_TIME = 150;
    private Thread mThread;

    private String mPrePackageName;
    private boolean isInterrupt;
    private volatile boolean isPause;
    private final Object object = new Object();
    private AppMonitor(){
    }

    public static final class AppMonitorHolder{
        static AppMonitor INSTANCE = new AppMonitor();
    }


    private void startAppMonitor(){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isInterrupt){
                  if(isPause){
                      try {
                          mThread.wait();
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
                }
            }
        });
        mThread.start();
    }

    public void interruptMonitor(){
        mThread.interrupt();
        isInterrupt = true;
    }

    public void pauseMonitor(){
        isPause = true;
    }

    public void resumeMonitor(){
        isPause = false;
        mThread.notify();
    }
}
