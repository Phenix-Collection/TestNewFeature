package com.example.wenjunzhong.testnewfeature;

import android.app.Application;

/**
 * Created by wenjun.zhong on 2016/11/9.
 */

public class TestApplication extends Application {

    public static long startTime;

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = System.currentTimeMillis();
    }
}
