package eric.flashlight;

/**
*
 * Created by wenjun.zhong on 2017/8/4.
 */

public final class FlashLightExceptionCode {
    private FlashLightExceptionCode(){}

    //被其他应用占用
    public final static int IS_USED = 1;

    //无法开启闪光灯
    public final static int FLASH_EXCEPTION = 2;
}
