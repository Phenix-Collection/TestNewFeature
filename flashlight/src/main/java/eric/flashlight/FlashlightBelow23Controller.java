package eric.flashlight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import eric.flashlight.utils.LogUtils;


/**
*
 * Created by wenjun.zhong on 2017/8/4.
 */

public class FlashlightBelow23Controller extends FlashLight {
    private static final String TAG = "Flashlight";
    private boolean isHasFlash = true;
    private Camera mCamera;
    private boolean isTurnedOn;
    public FlashlightBelow23Controller(Context context){
        isHasFlash = context.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        isTurnedOn = false;

    }

    @Override
    public void turnOn() {
        if(!isHasFlash){
            LogUtils.e(TAG, "This phone does not have flash light!!!!");
            return;
        }
        if(mCamera == null){
            try {
                mCamera = Camera.open();
            }catch (Throwable e){
                e.printStackTrace();
                // TODO: 2017/8/4 闪光灯占用

                LogUtils.e(TAG, "当前闪光灯被占用");
                if(mListener != null){
                    mListener.flashLightException(FlashLightExceptionCode.IS_USED);
                }
                return ;
            }
        }
        if(mCamera != null && !isTurnedOn)
        {
            //打开闪光灯
            Camera.Parameters parameter = mCamera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameter);
            mCamera.startPreview();
            isTurnedOn = true;
        }

        if(mListener != null){
            mListener.flashLightStateChange(isTurnedOn);
        }
    }

    @Override
    public void turnOff() {
        if(mCamera != null){
            mCamera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mCamera.getParameters());
            mCamera.stopPreview();
            isTurnedOn = false;
        }

        if(mListener != null){
            mListener.flashLightStateChange(isTurnedOn);
        }
    }

    @Override
    public boolean getSwitchState() {
        return isTurnedOn;
    }

    @Override
    void destroy() {
        if(mCamera != null){
            turnOff();
            mCamera.release();
            mCamera = null;
        }
    }
}
