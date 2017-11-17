package eric.flashlight;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.Timer;
import java.util.TimerTask;

import eric.flashlight.utils.LogUtils;

/**
 * Manages the flashlight.
 */

public class FlashlightController implements IFlashLightListener{
    private static final String TAG = "FlashlightController";
    private final Object locker = new Object();

    private FlashLight mFlashLight;
    private boolean isTurnedOn = false;
    private IFlashLightListener mListener;
    private FlashFrequency mFlashFrequency;
    private boolean isInterrupted = false;
    private Thread mThread;
    private long mAutoOffTime = -1;

    private Timer mTimer;
    private TimerTask mTimerTask;


    public FlashlightController(Context context, IFlashLightListener lightListener, long autoTurnOffTime){
        mListener = lightListener;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            mFlashLight = new FlashlightAbove23Controller(context);
        }else{
            mFlashLight = new FlashlightBelow23Controller(context);
        }

        mFlashFrequency = FlashFrequency.ALWAYS_BRIGHT;
        mFlashLight.setFlashLightListener(this);
        mAutoOffTime = autoTurnOffTime;
        isTurnedOn = false;
    }


    public void setAutoOffTime(long time){
        if(mAutoOffTime == time){
            return ;
        }
        mAutoOffTime = time;
        cancelTimer();
        if(isTurnedOn && mAutoOffTime > 0){
            startTimer(mAutoOffTime);
        }
    }


    public void setFlashLight(){
        isTurnedOn = !isTurnedOn;
        if(isTurnedOn){
            openFlashLight();
            startTimer(mAutoOffTime);
        }else{
            closeFlashLight();
        }
    }

    @Override
    public void flashLightStateChange(boolean enable) {
        if(mFlashFrequency.ordinal() != FlashFrequency.ALWAYS_BRIGHT.ordinal()){
            return ;
        }
        isTurnedOn = enable;
        if(mListener != null){
            mListener.flashLightStateChange(enable);
        }
    }

    @Override
    public void flashLightException(int exceptionCode) {
        isTurnedOn = false;
        isInterrupted = true;
        interruptedThread();
        cancelTimer();
        if(mListener != null){
            mListener.flashLightException(exceptionCode);
        }
    }

    public void changeFlashFrequency(FlashFrequency flashFrequency){
        if(mFlashFrequency.ordinal() == flashFrequency.ordinal()){
            return;
        }
        synchronized (locker) {
            mFlashFrequency = flashFrequency;
            mFlashFrequency.reset();
        }

        LogUtils.w(TAG, "changeFlashFrequency: " + mFlashFrequency.toString());
        if(isTurnedOn) {
            openFlashLight();
        }
    }

    private void openFlashLight(){
        if(mFlashFrequency.ordinal() == FlashFrequency.ALWAYS_BRIGHT.ordinal()){
            isInterrupted = true;
            interruptedThread();
            isTurnedOn = true;
            mFlashLight.turnOn();
            return;
        }

        isInterrupted = false;
        isTurnedOn = true;
        if(mThread == null || mThread.isInterrupted()){
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(mListener != null){
                        mListener.flashLightStateChange(true);
                    }
                    while (!isInterrupted){
                        mFlashLight.turnOn();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(isInterrupted){
                            break;
                        }
                        mFlashLight.turnOff();

                        final long time;
                        synchronized (locker) {
                            time = mFlashFrequency.nextFrequency();
                        }
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mThread.start();
        }
    }

    private void closeFlashLight(){
        isTurnedOn = false;
        isInterrupted = true;
        interruptedThread();
        mFlashLight.turnOff();
        if(mListener != null) {
            mListener.flashLightStateChange(false);
        }
        cancelTimer();
        synchronized (locker) {
            mFlashFrequency.reset();
        }
    }


    private void interruptedThread(){
        if(mThread != null){
            try {
                mThread.interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
            mThread = null;
        }
    }


    public void destroy(){
        isInterrupted = true;
        interruptedThread();
        mFlashLight.turnOff();
        isTurnedOn = false;
        mFlashLight.destroy();
        cancelTimer();
        mListener = null;
    }

    private void cancelTimer(){
        if(mTimerTask != null ){
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void startTimer(long autoTurnOffTimer){
        if(autoTurnOffTimer <= 0){
            return ;
        }

        if(mTimer == null){
            mTimer = new Timer("autoTurnOff");
        }

        if(mTimerTask == null){
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    closeFlashLight();
                }
            };
        }
        mTimer.schedule(mTimerTask, autoTurnOffTimer);
    }
}
