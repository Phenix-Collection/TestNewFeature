package eric.flashlight;

/**
*
 * Created by wenjun.zhong on 2017/8/4.
 */

public abstract class FlashLight {
    protected IFlashLightListener mListener;
    public void setFlashLightListener(IFlashLightListener lightListener){
        this.mListener = lightListener;
    }
    abstract void turnOn();
    abstract void turnOff();
    abstract boolean getSwitchState();
    abstract void destroy();
}
