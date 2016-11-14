package com.example.wenjunzhong.testnewfeature;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.wenjunzhong.testnewfeature.alarm.AlarmManagerUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            View statusBarView = findViewById(R.id.status_bar_view);
            statusBarView.getLayoutParams().height = getStatusBarHeight();

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_1:
                // gotoActivity(SnackBarActivity.class);
                // startReadThred();
                AlarmManagerUtil.startAlarm(this.getApplicationContext(), "2016-12-01 14:34:07");
                break;
            case R.id.button_2:
                gotoActivity(ToolbarScrollActivity.class);
                break;
            case R.id.button_3:
                gotoActivity(SwipeRefreshActivity.class);
                break;
            case R.id.button_4:
                gotoActivity(TextInputlayoutActivity.class);
                break;
            case R.id.button_5:
                gotoActivity(TestFragmentActivity.class);
                break;
            default:
                break;
        }
    }

    private void gotoActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void startReadThred() {
        new ReadThread().start();
    }
}
