package com.example.wenjunzhong.testnewfeature.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 * Created by wenjun.zhong on 2017/3/7.
 */

public class NotificationItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mSize;
    private int mOrientation;
    private static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL = LinearLayoutManager.VERTICAL;
    private int mItemMarginStartEnd;

    public NotificationItemDecoration(Context context, @ColorRes int color, @DimenRes int size) {
        mOrientation = HORIZONTAL;
        mDividerDrawable = new ColorDrawable(ContextCompat.getColor(context, color));
        mSize = context.getResources().getDimensionPixelSize(size);
        mItemMarginStartEnd = 0;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL) {
            drawHorizontalLine(c, parent, state);
        } else {
            drawVerticalLine(c, parent, state);
        }
    }

    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft() + mItemMarginStartEnd;
        final int right = parent.getWidth() - parent.getPaddingRight() - mItemMarginStartEnd;

        final int count = parent.getChildCount();
        for (int index = 0; index < count; index++) {
            final View childView = parent.getChildAt(index);

            final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) childView.getLayoutParams();
            final int top = childView.getBottom() + lp.bottomMargin;
            final int bottom = top + mSize;
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop() + mItemMarginStartEnd;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - mItemMarginStartEnd;

        final int count = parent.getChildCount();
        for (int index = 0; index < count; index++) {
            final View childView = parent.getChildAt(index);

            final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) childView.getLayoutParams();
            final int left = childView.getRight() + lp.rightMargin;
            final int right = left + mSize;
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL) {
            outRect.set(mItemMarginStartEnd, 0, mItemMarginStartEnd, mSize);
        } else {
            outRect.set(0, mItemMarginStartEnd, mSize, mItemMarginStartEnd);
        }
    }
}
