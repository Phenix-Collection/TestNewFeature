package com.example.wenjunzhong.testnewfeature.services;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2016/12/27.
 */

public class DeviceService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 自动获得Admin权限以及锁屏
        DevicePolicyManager dManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName name = new ComponentName(getApplicationContext(), DeviceService.class);//当前activity和获得权限的receiver
        boolean isActive = dManager.isAdminActive(name);
        Log.w("deviceService", isActive + "");
    }
}
