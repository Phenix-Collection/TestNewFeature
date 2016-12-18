package com.example.wenjunzhong.testnewfeature.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2016/11/11.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(AlarmManagerUtil.TAG, "onReceive");
        SendNotification.sendNotification(context);
    }
}
