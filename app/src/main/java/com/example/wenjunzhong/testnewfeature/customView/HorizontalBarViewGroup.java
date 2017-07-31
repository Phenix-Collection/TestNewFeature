package com.example.wenjunzhong.testnewfeature.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * Created by wenjun.zhong on 2017/7/31.
 */

public class HorizontalBarViewGroup extends ViewGroup {
    public HorizontalBarViewGroup(Context context) {
        super(context);
    }

    public HorizontalBarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalBarViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalBarViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int preChildRight = 0;
        int preChildBottom = 0;
        for (int i = 0; i < count; i++) {
            final View childView = getChildAt(i);
            if (childView == null || childView.getVisibility() == View.GONE) {
                continue;
            }
            childView.layout(preChildRight, preChildBottom, preChildRight + childView.getMeasuredWidth(),
                    preChildBottom + childView.getMeasuredHeight());
            preChildBottom = preChildBottom + childView.getMeasuredHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }
}
