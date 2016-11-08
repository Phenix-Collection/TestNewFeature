package com.example.wenjunzhong.testnewfeature;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by eric on 16/9/16.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "Fragment";
    private static final int PAGE_COUNT = 3;


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
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                return new Fragment1();
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
