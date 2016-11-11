package com.example.wenjunzhong.testnewfeature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by wenjun.zhong on 2016/9/28.
 */

public class TestFragmentActivity extends AppCompatActivity {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "Fragment";

    private static final int MAIN_TAB_HOME = 0;
    private static final int MAIN_TAB_PLANS = 1;
    private static final int MAIN_TAB_HELP = 2;


    private MainFragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private int[] mTitleId;
    private int mSelectedTab;
    private boolean isToPlanFragment = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment_layout);
        isToPlanFragment = getIntent().getBooleanExtra("isToPlanFragment", false);
        if (DEBUG) {
            Log.w("isToPlan", "onCreate isToPlanFragment: " + isToPlanFragment);
        }
        setupView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isToPlanFragment = intent.getBooleanExtra("isToPlanFragment", false);
        if (isToPlanFragment) {
            gotoPlansFragment();
        }
        if (DEBUG) {
            Log.w("isToPlan", "onNewIntent isToPlanFragment: " + isToPlanFragment);
        }
    }

    private void setupView() {
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        mViewPager.setAdapter(mAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        final int count = mTabLayout.getTabCount();
        if (DEBUG) {
            Log.w(TAG, "tablayout item size: " + count);
        }
        mTitleId = new int[count];
        for (int i = 0; i < count; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if ((isToPlanFragment && i == 1) || (!isToPlanFragment && i == 0)) {
                setSelectedTab(tab);
                if (i == 1) {
                    gotoPlansFragment();
                }
            } else {
                setUnSelectedTab(tab);
            }
        }
        mTitleId[0] = R.string.home;
        mTitleId[1] = R.string.help;


        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                if (DEBUG) {
                    Log.w(TAG, "onTabSelected: " + tab.getPosition());
                }
                setSelectedTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                if (DEBUG) {
                    Log.w(TAG, "onTabUnselected: " + tab.getPosition());
                }
                setUnSelectedTab(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                if (DEBUG) {
                    Log.w(TAG, "onTabReselected: " + tab.getPosition());
                }
            }
        });

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.float_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseFragment) (mAdapter.getItem(mSelectedTab))).onClickRefresh(v);
            }
        });
    }

    private void setSelectedTab(TabLayout.Tab tab) {
        if (tab == null) {
            return;
        }
        final int position = tab.getPosition();
        mSelectedTab = position;
        switch (position) {
            case MAIN_TAB_HOME:
                tab.setIcon(R.drawable.tab_home_active);
                break;
            case MAIN_TAB_PLANS:
                tab.setIcon(R.drawable.tab_plans_active);
                break;
            case MAIN_TAB_HELP:
                tab.setIcon(R.drawable.tab_help_active);
                break;
        }
    }

    private void setUnSelectedTab(TabLayout.Tab tab) {
        if (tab == null) {
            return;
        }
        final int position = tab.getPosition();
        switch (position) {
            case MAIN_TAB_HOME:
                tab.setIcon(R.drawable.tab_home);
                break;
            case MAIN_TAB_PLANS:
                tab.setIcon(R.drawable.tab_plans);
                break;
            case MAIN_TAB_HELP:
                tab.setIcon(R.drawable.tab_help);
                break;
        }
    }

    public void gotoPlansFragment() {
        mViewPager.setCurrentItem(1, true);
    }

    // public void onClickItem(View view) {
    // Fragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
    // if (fragment instanceof BaseFragment) {
    // ((BaseFragment) fragment).onClickItem(view, this);
    // }
    // }
}
