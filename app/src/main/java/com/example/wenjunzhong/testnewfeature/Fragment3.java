package com.example.wenjunzhong.testnewfeature;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by wenjun.zhong on 2016/9/28.
 */

public class Fragment3 extends BaseFragment {
    private static final String TAG = "Fragment3";
    View rootView;
    Button button;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment2_layout, container, false);
            button = (Button) rootView.findViewById(R.id.fragment1_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setEnabled(false);
                }
            });
            Log.w(TAG, "onCreateView load root view ");
        }
        Log.w(TAG, "onCreateView");
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(TAG, "onSaveInstanceState");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w(TAG, "onStop");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        Log.w(TAG, "onDestroyOptionsMenu");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.w(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.w(TAG, "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.w(TAG, "onDestroyView");
    }

    @Override
    public void onClickRefresh(View v) {
        Snackbar snackbar = Snackbar.make(v, "abcd--3", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
