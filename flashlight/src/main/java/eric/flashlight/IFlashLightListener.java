package eric.flashlight;

/**
*
 * Created by wenjun.zhong on 2017/8/4.
 */

public interface IFlashLightListener {

    void flashLightStateChange(boolean enable);
    void flashLightException(int exceptionCode);
}
