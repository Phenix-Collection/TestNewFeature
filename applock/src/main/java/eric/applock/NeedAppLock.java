package eric.applock;

import android.text.TextUtils;
import android.util.Log;

import java.awt.font.TextAttribute;
import java.util.HashMap;

/**
 * Created by wenjun.zhong on 2017/7/24.
 */

public final class NeedAppLock {
    public static final String TAG = "AppLock";
    private NeedAppLock(){}

    private static HashMap<String, Boolean> needLock = new HashMap<>(12);
    static {
        init();
    }

    private static void init(){
        needLock.put("com.jrdcom.filemanager", true);
        needLock.put("com.tencent.mobileqq", true);
        needLock.put("com.sohu.newsclient", true);
        needLock.put("com.facebook.katana", true);
        needLock.put("com.example.wenjunzhong.testnewfeature", true);
        needLock.put("com.twitter.android", true);
        needLock.put("com.android.dialer", true);
    }

    public static boolean isNeedLock(String packageName){
        Log.w(TAG, "isNeedLock packageName: " + packageName);
        if(TextUtils.isEmpty(packageName)){
            return false;
        }
        Boolean result = needLock.get(packageName);
        if(result == null){
            return false;
        }else{
            return  result;
        }
    }

    public static void unLockState(String packageName ){
        if(needLock.containsKey(packageName)) {
            needLock.put(packageName, false);
        }
    }

    public static void resetLockState(){
        init();
    }
}
