package com.example.wenjunzhong.testnewfeature;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by wenjun.zhong on 2016/11/9.
 */

public class TestApplication extends Application {

    public static long startTime;
    private static TestApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        startTime = System.currentTimeMillis();
        BlockCanary.install(this, new AppBlockCanaryContext());
        TCAgent.LOG_ON=true;
    }


    public static TestApplication getInstance() {
        return instance;
    }
}
