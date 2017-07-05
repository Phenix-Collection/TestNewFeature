package com.example.wenjunzhong.testnewfeature;

import android.app.Application;
import android.util.Log;

import com.github.moduth.blockcanary.*;

import java.lang.reflect.Method;

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
      //  TCAgent.LOG_ON=true;
        setLeakCanary();
    }


    public static TestApplication getInstance() {
        return instance;
    }


    private  void setLeakCanary(){
        if(BuildConfig.FLAVOR.equals("monkey")){
            try{
                Class leakCanaryClass = Class.forName("eric.leakcanary.LeakCanaryForTest");
                Method method = leakCanaryClass.getDeclaredMethod("install", Application.class);
                method.setAccessible(true);
                method.invoke(null, this);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
