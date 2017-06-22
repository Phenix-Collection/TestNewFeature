package com.example.wenjunzhong.testnewfeature.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 * Created by wenjun.zhong on 2017/6/22.
 */

public class RecyclerViewOnClickListener implements RecyclerView.OnItemTouchListener {

    public interface OnItemClickListener{
        void onItemClick(View view, int adapterPosition, int layoutPosition);
        void onItemLongClick(View view, int position);
        void onItemDoubleClick(View view, int position);
    }


    private GestureDetector mGestureDetector;
    private OnItemClickListener mItemClickListener;
    private RecyclerView mRecyclerView;

    public RecyclerViewOnClickListener(Context context, OnItemClickListener listener, RecyclerView recyclerView){
        mItemClickListener = listener;
        mRecyclerView = recyclerView;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if(mItemClickListener != null && mRecyclerView != null) {
                    View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(childView != null){
                        mItemClickListener.onItemClick(childView, mRecyclerView.getChildAdapterPosition(childView),
                                mRecyclerView.getChildLayoutPosition(childView));
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if(mItemClickListener != null && mRecyclerView != null) {
                    View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(childView != null){
                        mItemClickListener.onItemLongClick(childView, mRecyclerView.getChildAdapterPosition(childView));
                    }
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(mItemClickListener != null && mRecyclerView != null) {
                    View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(childView != null){
                        mItemClickListener.onItemDoubleClick(childView, mRecyclerView.getChildAdapterPosition(childView));
                        return true;
                    }
                }
                return false;
            }
        });

    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
