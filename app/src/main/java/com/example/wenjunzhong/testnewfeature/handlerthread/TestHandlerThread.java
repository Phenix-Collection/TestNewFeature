package com.example.wenjunzhong.testnewfeature.handlerthread;

import android.util.Log;

import static com.example.wenjunzhong.testnewfeature.handlerthread.HandlerThreadManager.TAG;

/**
 * Created by wenjun.zhong on 2017/2/22.
 */

public class TestHandlerThread {

    private static final int ACCOUNT = 8;
    private Thread mThread1;
    private Thread mthread2;

    private HandlerThreadManager mManager;

    private int count1 = 0;
    private int count2 = 0;

    public TestHandlerThread() {

        mManager = new HandlerThreadManager();

        mThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (count1 >= ACCOUNT) {
                        Log.w(TAG, "thread 1 done");
                        break;
                    }
                    mManager.handlerTask();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    count1 += 1;
                }
            }
        });

        mthread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (count2 >= ACCOUNT) {
                        Log.w(TAG, "thread 2 done");
                        break;
                    }
                    mManager.handlerTask();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    count2 += 1;
                }
            }
        });

    }

    public void start() {
        mThread1.start();
        mthread2.start();
    }

    public void stop() {
        if (mthread2 != null) {
            mthread2.interrupt();
            mthread2 = null;
        }

        if (mThread1 != null) {
            mThread1.interrupt();
            mThread1 = null;
        }
    }
}
