package com.example.wenjunzhong.testnewfeature.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/**
 *
 * Created by wenjun.zhong on 2016/11/11.
 */

public class AlarmManagerUtil {
    public static final String ALARM_ACTION = "android.alarm.voyager.ALARM_RECEIVER";
    public static final int REQUEST_CODE = 101;

    public static void setAlarm(Context context, boolean isRepeat) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ALARM_ACTION);
        PendingIntent sender =
                PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MILLISECOND, 10 * 1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 0, sender);
        } else {
            if (!isRepeat) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 0, sender);
            }
        }
    }


}
