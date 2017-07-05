package com.example.wenjunzhong.testnewfeature.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wenjunzhong.testnewfeature.R;

/**
 * Created by wenjun.zhong on 2016/9/28.
 */

public class Fragment2 extends BaseFragment {
    private static final String TAG = "Fragment2";
    View rootView;
    Button button;
    TextView textView;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment2_layout, container, false);
            button = (Button) rootView.findViewById(R.id.fragment1_button);
            textView = (TextView) rootView.findViewById(R.id.fragment_text);
            // button.setOnClickListener(new View.OnClickListener() {
            // @Override
            // public void onClick(View v) {
            // count++;
            // updateText();
            // }
            // });
            Log.w(TAG, "onCreateView load root view ");
        }
        Log.w(TAG, "onCreateView");
        return rootView;
    }

    private void updateText() {
        textView.setText(String.valueOf(count));
    }

    public void onClickItem(View view) {
        switch (view.getId()) {
            case R.id.fragment1_button:
                count++;
                updateText();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.w(TAG, "onActivityCreated");
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
        Snackbar snackbar = Snackbar.make(v, "abcd--2", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
