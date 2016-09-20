package com.example.wenjunzhong.testnewfeature;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Locale;
import java.util.Observable;

/**
 * Created by wenjun.zhong on 2016/4/19.
 */
public class SwipeRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String TAG = "abcd123";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        Log.d(TAG, "onCreate: current thread id " + Thread.currentThread().getId());
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(100);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.black
        );
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: current thread id " + Thread.currentThread().getId());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                RecyclerAdapter.mList.add(0, String.format(Locale.ENGLISH, "下拉刷新#%d", System.currentTimeMillis()));
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 8000);
    }
}
