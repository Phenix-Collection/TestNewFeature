package com.example.wenjunzhong.testnewfeature.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * Created by wenjun.zhong on 2016/11/11.
 */
public class AlarmManagerUtil {
    public static final String TAG = "Alarm";
    private static final String ALARM_EXPIRED_ACTION = "android.alarm.voyager.ALARM_EXPIRED_ACTION";
    private static final String ALARM_MONTH_ACTION = "android.alarm.voyager.ALARM_MONTH_ACTION";
    private static final String ALARM_DAY_ACTION = "android.alarm.voyager.ALARM_DAY_ACTION";
    private static final int REQUEST_CODE = 101;

    private static final long MILLISECOND_OF_MONTH = 30L * 24 * 60 * 60 * 1000;
    private static final long MILLISECOND_OF_DAY = 24 * 60 * 60 * 1000L;
    private static final long MILLISECOND_OF_MIN_INTERVAL = 60 * 60 * 1000L;

    public static void startAlarm(Context context, String dateStr) {
        AlarmInfo info = calculateAlarmDelayInfo(dateStr);
        if (info == null) {
            Log.w(TAG, "info == null");
            return;
        }
        Log.w(TAG, info.toString());
        // this is for test
        info.delayTime = 3000;
        info.action = ALARM_MONTH_ACTION;
        // end
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setJobScheduler(context, info.delayTime, info.action);
        } else {
            setAlarm(context, info.delayTime, info.action);
        }
    }

    private static void setAlarm(Context context, long delayTime, String action) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(action);
        PendingIntent sender =
                PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        long absoluteDelayTime = System.currentTimeMillis() + delayTime;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, absoluteDelayTime, 0, sender);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, absoluteDelayTime, sender);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setJobScheduler(Context context, long delayTime, String action) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancelAll();
        JobInfo.Builder builder =
                new JobInfo.Builder(1, new ComponentName(context.getPackageName(), JobSchedulerService.class.getName()));
        builder.setPersisted(true).setMinimumLatency(delayTime);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setTriggerContentMaxDelay(delayTime + 10000);
        }

        if (jobScheduler.schedule(builder.build()) <= 0) {
            setAlarm(context, delayTime, action);
        }
    }

    @Nullable
    private static Date stringConvertToDate(String stringDate) {
        if (TextUtils.isEmpty(stringDate)) {
            Log.e("date convert", "string time convert to date stringDate is null");
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算闹钟延迟时间，单位为ms(毫秒)
     * 
     * @return
     */
    public static AlarmInfo calculateAlarmDelayInfo(String validTime) {
        Date validDate = stringConvertToDate(validTime);
        if (validDate == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(validDate);

        long intervalTime = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (intervalTime <= 0) {
            // 已过期
            return new AlarmInfo(3000, ALARM_EXPIRED_ACTION);
        } else if (intervalTime <= MILLISECOND_OF_MIN_INTERVAL) {
            //
            return null;
        } else if (intervalTime <= MILLISECOND_OF_DAY) {
            return new AlarmInfo(intervalTime - MILLISECOND_OF_MIN_INTERVAL, ALARM_DAY_ACTION);
        } else if (intervalTime <= MILLISECOND_OF_MONTH) {
            return new AlarmInfo(intervalTime - MILLISECOND_OF_DAY, ALARM_MONTH_ACTION);
        } else {
            return null;
        }
    }

    /**
     * 购买成功后取消闹钟
     * 
     * @param context
     */
    public static void cancelAlarm(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.cancelAll();
        } else {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ALARM_DAY_ACTION);
            PendingIntent sender =
                    PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(sender);
        }
    }
}
