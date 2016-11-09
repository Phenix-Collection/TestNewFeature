package com.example.wenjunzhong.testnewfeature;

import android.util.Log;

import com.example.wenjunzhong.testnewfeature.utils.DataParseUtil;
import com.example.wenjunzhong.testnewfeature.utils.RC4;



/**
 * Created by eric on 16/8/3.
 */

public class ReadThread extends Thread {

    private static final String TAG = "abcd123ReadVirtual";
    private static final boolean DEBUG = false;
    private boolean isRunning;


    public ReadThread() {
        super("ReadVirtualThread");
    }

    @Override
    public void run() {
        isRunning = true;
        // Allocate the buffer for a single packet.
        final byte[] byteBuffer = new byte[32767];
        final byte[] keys = new byte[DataParseUtil.KEY_LENGTH];
        final byte[] outKeys = new byte[RC4.OUT_KEY_LENGTH];
        int length = 0;
        int result;
        long time;

        while (isRunning) {
            if (!isRunning) {
                return;
            }
            for (int i = 0; i <= 1400; i++) {
                byteBuffer[i] = (byte) i;
            }
            length = 1401;
            time = System.currentTimeMillis();
            DataParseUtil.decode(byteBuffer, length, keys, outKeys);
            Log.w("testTime", "rc encode time: " + (System.currentTimeMillis() - time));
            if (!isRunning) {
                return;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep() {}

    public void stopRead() {
        isRunning = false;
    }
}
