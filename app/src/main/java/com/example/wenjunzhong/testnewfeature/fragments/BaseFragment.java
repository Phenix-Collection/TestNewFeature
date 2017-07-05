package com.example.wenjunzhong.testnewfeature.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wenjunzhong.testnewfeature.statistical.StatisticalAgent;

/**
 * Created by eric on 16/9/16.
 */

public class BaseFragment extends Fragment {
    RecyclerView.RecycledViewPool mPool;
    public void onClickRefresh(View v) {}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
