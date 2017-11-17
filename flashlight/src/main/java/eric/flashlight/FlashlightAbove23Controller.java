package eric.flashlight;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import eric.flashlight.utils.LogUtils;


@TargetApi(23)
public class FlashlightAbove23Controller extends FlashLight {

    private static final String TAG = "Flashlight";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private CameraManager mCameraManager;
    private Handler mHandler;

    private boolean mFlashlightEnabled;

    private final String mCameraId;
    private boolean mTorchAvailable;

    public FlashlightAbove23Controller(Context mContext) {
        mCameraManager = (CameraManager) mContext.getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try {
            cameraId = getCameraId();
        } catch (Throwable e) {
            Log.e(TAG, "FlashlightAbove23Controller Couldn't initialize.", e);
            return;
        } finally {
            mCameraId = cameraId;
        }
        mFlashlightEnabled = false;
    }

    private void setFlashlight(boolean enabled) {
        boolean isException = false;
        synchronized (this) {
            if (mFlashlightEnabled != enabled) {
                mFlashlightEnabled = enabled;
                try {
                    mCameraManager.setTorchMode(mCameraId, enabled);
                } catch (CameraAccessException e) {
                    Log.e(TAG, "Couldn't set torch mode", e);
                    mFlashlightEnabled = false;
                    isException = true;
                }catch (RuntimeException e){
                    e.printStackTrace();
                    mFlashlightEnabled = false;
                    isException = true;
                }
            }
        }
        if(mListener != null){
            if(isException){
                mListener.flashLightException(FlashLightExceptionCode.FLASH_EXCEPTION);
            }else {
                mListener.flashLightStateChange(mFlashlightEnabled);
            }
        }
    }

    private String getCameraId() throws CameraAccessException {
        String[] ids = mCameraManager.getCameraIdList();
        for (String id : ids) {
            CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
            Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
            if (flashAvailable != null && flashAvailable
                    && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return id;
            }
        }
        return null;
    }


    private synchronized void ensureHandler() {
        if (mHandler == null) {
            HandlerThread thread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
            thread.start();
            mHandler = new Handler(thread.getLooper());
        }
    }

    private final CameraManager.TorchCallback mTorchCallback =
            new CameraManager.TorchCallback() {

        @Override
        public void onTorchModeUnavailable(String cameraId) {
            LogUtils.w(TAG, "onTorchModeUnavailable: " + cameraId);
            if (TextUtils.equals(cameraId, mCameraId)) {
                setCameraAvailable(false);
            }
        }

        @Override
        public void onTorchModeChanged(String cameraId, boolean enabled) {
            LogUtils.w(TAG, "onTorchModeChanged: " + cameraId + ", enabled: " + enabled);
            if (TextUtils.equals(cameraId, mCameraId)) {
                setCameraAvailable(true);
                setTorchMode(enabled);
            }
        }

        private void setCameraAvailable(boolean available) {
            boolean changed;
            synchronized (FlashlightAbove23Controller.this) {
                changed = mTorchAvailable != available;
                mTorchAvailable = available;
            }
        }

        private void setTorchMode(boolean enabled) {
            boolean changed;
            synchronized (FlashlightAbove23Controller.this) {
                changed = mFlashlightEnabled != enabled;
                mFlashlightEnabled = enabled;
            }
        }
    };

    @Override
    public void turnOn() {
        setFlashlight(true);
    }

    @Override
    public void turnOff() {
        setFlashlight(false);
    }

    @Override
    public boolean getSwitchState() {
        return mFlashlightEnabled;
    }

    @Override
    void destroy() {
        mCameraManager = null;
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
