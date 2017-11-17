package eric.flashlight.utils;

import android.util.Log;

import eric.flashlight.BuildConfig;

/**
 * @Description: Log打印类
 * @date 2017/2/17 下午3:00
 * @copyright TCL-MIE
 */
public class LogUtils {

    private static String TAG = "LogUtils";
    private static boolean logSwitch = BuildConfig.DEBUG;

    public static boolean isLogSwitch() {
        return logSwitch;
    }

    public static void setLogSwitch(boolean logSwitch) {
        LogUtils.logSwitch = logSwitch;
    }

    public static void e(String content) {
        e(TAG, content);
    }

    public static void e(String tag, String content) {
        if (logSwitch) {
            Log.e(tag, content);
        }
    }

    public static void w(String content) {
        w(TAG, content);
    }

    public static void w(String tag, String content) {
        if (logSwitch) {
            Log.w(tag, content);
        }
    }

    public static void i(String content) {
        i(TAG, content);
    }

    public static void i(String tag, String content) {
        if (logSwitch) {
            Log.i(tag, content);
        }
    }

    public static void d(String content) {
        d(TAG, content);
    }

    public static void d(String tag, String content) {
        if (logSwitch) {
            Log.d(tag, content);
        }
    }

    public static void v(String content) {
        v(TAG, content);
    }

    public static void v(String tag, String content) {
        if (logSwitch) {
            Log.v(tag, content);
        }
    }

    public static void logException(String tag,Exception e){
        if(logSwitch){
            String msg = e.getMessage();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] elements =  e.getStackTrace();
            if(elements != null){
                for(StackTraceElement element:elements){
                    sb.append("    at "+element.toString()+"\n");
                }
            }
            Log.e(TAG,"java.lang.Exception: "+msg);
            Log.e(TAG,sb.toString());
        }
    }

    public static void logException(Exception e){
        logException(TAG,e);
    }
}
