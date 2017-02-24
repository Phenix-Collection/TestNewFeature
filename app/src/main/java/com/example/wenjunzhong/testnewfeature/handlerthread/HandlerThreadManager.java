package com.example.wenjunzhong.testnewfeature.handlerthread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2017/2/22.
 */

public class HandlerThreadManager {
    public static final String TAG = "HandlerThreadManager";
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private long count;

    public HandlerThreadManager() {
        mHandlerThread = new HandlerThread("manager", Process.THREAD_PRIORITY_DEFAULT);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        count = 0l;
    }

    public void handlerTask() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                count += 1;
                try {
                    Thread.sleep(3000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.w(TAG, "handlerTask count: " + count + "," + Thread.currentThread().getName());
            }
        });
    }
}
