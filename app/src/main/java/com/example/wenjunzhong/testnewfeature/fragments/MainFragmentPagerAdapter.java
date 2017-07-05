package com.example.wenjunzhong.testnewfeature.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.wenjunzhong.testnewfeature.BuildConfig;
import com.example.wenjunzhong.testnewfeature.fragments.Fragment1;
import com.example.wenjunzhong.testnewfeature.fragments.Fragment2;
import com.example.wenjunzhong.testnewfeature.fragments.Fragment3;

/**
 * Created by eric on 16/9/16.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "Fragment";
    private static final int PAGE_COUNT = 3;
    RecyclerView.RecycledViewPool mPool = new RecyclerView.RecycledViewPool(){
        @Override
        public void clear() {
            super.clear();
        }

        @Override
        public void setMaxRecycledViews(int viewType, int max) {
            Log.w("test", "RecycledViewPool setMaxRecycledViews");
            super.setMaxRecycledViews(viewType, max);
        }

        @Override
        public RecyclerView.ViewHolder getRecycledView(int viewType) {
            Log.w("test", "RecycledViewPool getRecycledView viewType:" + viewType);
            return super.getRecycledView(viewType);
        }

        @Override
        public void putRecycledView(RecyclerView.ViewHolder scrap) {
            Log.w("test", "RecycledViewPool putRecycledView");
            super.putRecycledView(scrap);
        }
    };

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (DEBUG) {
            Log.w(TAG, "position: " + position);
        }
        switch (position) {
            case 0:
                Fragment1 f1 = new Fragment1();
                f1.mPool = mPool;
                return f1;
            case 1:
                return new Fragment2();
            case 2:
                Fragment4 f4 = new Fragment4();
                f4.mPool = mPool;
                return f4;
            default:
                Fragment1 f11 = new Fragment1();
                f11.mPool = mPool;
                return f11;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
