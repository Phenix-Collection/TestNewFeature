package com.example.wenjunzhong.testnewfeature.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenjunzhong.testnewfeature.R;

/**
 *
 * Created by wenjun.zhong on 2017/6/20.
 */

public class TestInflatLayout extends LinearLayout {
    private static final String TAG = "TestInflatLayout";

    private TextView textView1;
    private TextView textView2;
    private boolean isInflate1 = true;
    public TestInflatLayout(Context context) {
        super(context);
    }

    public TestInflatLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestInflatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestInflatLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initInflate(){
        int layoutId ;
        this.removeAllViews();
        if(isInflate1){
            layoutId = R.layout.layout_test_inflate_1;
            setBackgroundResource(R.color.white);
        }else{
            layoutId = R.layout.layout_test_inflate_2;
            setBackgroundResource(R.color.black);
        }
        isInflate1 = !isInflate1;
        inflate(getContext(), layoutId, this);
        textView1 = (TextView) findViewById(R.id.test_inflate_text1);
        textView2 = (TextView) findViewById(R.id.test_inflate_text2);
    }

    public void changeTextView2(){
        textView2.setText("change text view2");
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.w(TAG, this.getClass().getSimpleName() + " onFinishInflate");
    }
}
