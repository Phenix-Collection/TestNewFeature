package com.example.wenjunzhong.testnewfeature;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


/**
 * Created by zhongwenjun on 15/8/30
 */
public class SplashActivity extends BaseActivity {
    // splash界面停留时间（ms)
    private static final int SPLASH_STOP_TIME = 1 * 1000;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("startTime", "onCreate start: " + (System.currentTimeMillis() - TestApplication.startTime));
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // getWindow().setBackgroundDrawable(getDrawable(R.drawable.splash));


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_STOP_TIME);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.w("startTime", "onResume start: " + (System.currentTimeMillis() - TestApplication.startTime));
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.w("startTime", "onStop end: " + (System.currentTimeMillis() - TestApplication.startTime));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("startTime", "onPause end: " + (System.currentTimeMillis() - TestApplication.startTime));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("startTime", "onDestroy end: " + (System.currentTimeMillis() - TestApplication.startTime));
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
