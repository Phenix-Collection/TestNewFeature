package com.example.wenjunzhong.testnewfeature;

import android.animation.ObjectAnimator;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import com.example.wenjunzhong.testnewfeature.binder.BinderActivity;
import com.example.wenjunzhong.testnewfeature.notification.NotificationActivity;
import com.example.wenjunzhong.testnewfeature.services.DeviceAdminDemo;
import com.example.wenjunzhong.testnewfeature.services.DeviceService;
import com.example.wenjunzhong.testnewfeature.services.SystemDialogService;
import com.example.wenjunzhong.testnewfeature.statistical.StatisticalAgent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            View statusBarView = findViewById(R.id.status_bar_view);
            statusBarView.getLayoutParams().height = getStatusBarHeight();

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Log.w("onClick", " " + v.getId());
        switch(v.getId()){
            case R.id.button_1:
                // gotoActivity(SnackBarActivity.class);
                // startReadThred();
                startTestThread();
//                AlarmManagerUtil.startAlarm(this.getApplicationContext(), "2016-12-01 14:34:07");
//                StatisticalAgent.onEvent(this, "login", "1");
                break;
            case R.id.button_2:
                gotoActivity(ToolbarScrollActivity.class);
                StatisticalAgent.onEvent(this, "login", "2");
                break;
            case R.id.button_3:
                gotoActivity(SwipeRefreshActivity.class);
                StatisticalAgent.onEvent(this, "login", "3");
                break;
            case R.id.button_4:
                gotoActivity(TextInputlayoutActivity.class);
                StatisticalAgent.onEvent(this, "login", "4");
                break;
            case R.id.button_5:
                gotoActivity(TestFragmentActivity.class);
                StatisticalAgent.onEvent(this, "login", "5");
                break;
            case R.id.button_6:
                gotoActivity(NotificationActivity.class);
                break;
            case R.id.button_7:
                gotoActivity(WebViewActivity.class);
//                startServices(SystemDialogService.class);
//                deviceAdmin();
//                index++;
//                propertyAnimation(v, index);
//                String result = md5Encrypt("654321");
//                Log.w("md5", result);
                break;
            default:
                break;
        }
    }

    private void gotoActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private void startServices(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startService(intent);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void startReadThred() {
        new ReadThread().start();
    }

    private void startTestThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.w("talkingData", "start talkingData ");
                for (int i = 0; i < 30000; i++){
                    StatisticalAgent.onEvent(getApplicationContext(), "login", String.valueOf(i));
                    Log.w("talkingData", "tongji " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.w("talkingData", "end talkingData ");
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        startServices(SystemDialogService.class);
    }

    private void deviceAdmin(){
        // 自动获得Admin权限以及锁屏
        DevicePolicyManager dManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName name = new ComponentName(getApplicationContext(), DeviceAdminDemo.class);//当前activity和获得权限的receiver
        final int myUserId = UserHandle.myUserId();
//        dManager.setActiveAdmin(name, false, myUserId);
        try {
//            dManager.setProfileOwner(name, "SystemUI", myUserId);
            dManager.setActiveProfileOwner(name, "SystemUI");
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean isActive = dManager.isAdminActive(name);
        Log.w("deviceService", isActive + "");

        List<ComponentName> lists = dManager.getActiveAdmins();

        Log.w("deviceService", Arrays.toString(lists.toArray()));
    }

    private void propertyAnimation(View view, int index ){
        ObjectAnimator.ofFloat(view, "translationY", getResources().getDimensionPixelSize(R.dimen.button_height) * index).setDuration(100).start();
    }


    public static String md5Encrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            return " ";
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return " ";
        }
        byte[] bytes = new byte[0];
        try {
            bytes = md5.digest(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        String temp;
        for (byte b : bytes) {
            temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                builder.append(0);
            }
            builder.append(temp);
        }
        return builder.toString();
    }
}
