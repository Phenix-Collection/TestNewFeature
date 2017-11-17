package eric.flashlight.utils;

import android.content.Context;
import android.content.SharedPreferences;

import eric.flashlight.FlashApplication;

/**
 *
 * Created by wenjun.zhong on 2017/8/7.
 */

public final class PreferenceUtil {
    private static final String PREF_NAME = "settings_shared.pref";

    private static final String DEFAULT_IS_TURNED_ON = "default_is_turned_on";
    private static final String AUTO_TURN_OFF_TIME = "turn_off_time";

    private PreferenceUtil(){}


    public static boolean isTurnOnDefault(){
        SharedPreferences sp = FlashApplication.getAppContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(DEFAULT_IS_TURNED_ON, true);
    }

    public static void changeDefaultTurnOn(boolean isTurnOn){
        SharedPreferences sp = FlashApplication.getAppContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DEFAULT_IS_TURNED_ON, isTurnOn).apply();
    }

    public static long getAutoTurnOffTime(){
        SharedPreferences sp = FlashApplication.getAppContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getLong(AUTO_TURN_OFF_TIME, -1);
    }

    public static void changeAutoTurnOffTime(long time){
        SharedPreferences sp = FlashApplication.getAppContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(AUTO_TURN_OFF_TIME, time).apply();
    }
}
