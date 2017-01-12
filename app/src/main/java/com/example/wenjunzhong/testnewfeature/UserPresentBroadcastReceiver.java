package com.example.wenjunzhong.testnewfeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.wenjunzhong.testnewfeature.services.SystemDialogService;

/**
 * Created by dejun.xie on 2016/12/21.
 */
public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context, SystemDialogService.class);
        context.startService(intent1);
        Log.d("XDJ", "onReceive: 收到屏幕解锁广播...");
    }
}
