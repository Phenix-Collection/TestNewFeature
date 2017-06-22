package com.example.wenjunzhong.testnewfeature;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.wenjunzhong.testnewfeature.handlerthread.TestHandlerThread;
import com.example.wenjunzhong.testnewfeature.notification.NotificationActivity;
import com.example.wenjunzhong.testnewfeature.statistical.StatisticalAgent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
//                gotoActivity(SnackBarActivity.class);
                createNotification();
                break;
            case R.id.button_2:
                gotoActivity(WebViewActivity.class);
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
                // SystemClock.sleep(5100);
                // startServices(SystemDialogService.class);
                TestHandlerThread testHandlerThread = new TestHandlerThread();
                testHandlerThread.start();
                break;
            case R.id.button_8:
//                gotoActivity(RecyclerAnimationActivity.class);
                gotoActivity(RecyclerViewOnTouchItemListenerActivity.class);
                break;
            default:
                break;
        }
    }


    private void googleServices() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
//        Uri content_url = Uri.parse("https://play.google.com/store/apps/details?id=com.Mainews.news");
        Uri content_url = Uri.parse("tstream://tstream.app");
        intent.setData(content_url);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.Mainews.news"));
            startActivity(intent);
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


    int noticeIndex = 1;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction("abcd " + noticeIndex);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, noticeIndex, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder notifyBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("拦截: " + getResources().getString(R.string.app_name))
                .setContentText("内容:" + ": " + noticeIndex)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = notifyBuilder.build();
        nm.notify(noticeIndex, notification);
        noticeIndex++;
    }
}
