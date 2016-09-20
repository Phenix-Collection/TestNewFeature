package com.example.wenjunzhong.testnewfeature.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by wenjun.zhong on 2016/9/20.
 */

public class CustomerPasswordEditLayout extends RelativeLayout {
    public CustomerPasswordEditLayout(Context context) {
        this(context, null);
    }

    public CustomerPasswordEditLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerPasswordEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomerPasswordEditLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData();
    }

    private void initData(){

    }
}
