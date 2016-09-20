package com.example.wenjunzhong.testnewfeature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by wenjun.zhong on 2016/4/14.
 */
public class SnackBarActivity extends BaseActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snackbar_activity);
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.float_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "abcd", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
}
