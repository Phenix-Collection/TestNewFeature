package com.example.wenjunzhong.testnewfeature;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.wenjunzhong.testnewfeature.utils.QRCodeUtil;

/**
 *
 * Created by wenjun.zhong on 2017/8/1.
 */

public class QRActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        imageView = (ImageView) findViewById(R.id.qr_code);

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                imageView.setImageBitmap(QRCodeUtil
                        .createQRCode("http://www.lvxing.im/downloads/Voyager.apk", 500, 500));
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageView.setImageDrawable(null);
    }
}
