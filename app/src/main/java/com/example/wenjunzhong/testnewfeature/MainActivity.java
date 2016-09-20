package com.example.wenjunzhong.testnewfeature;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_1:
                gotoActivity(SnackBarActivity.class);
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
            default:
                break;
        }
    }

    private void gotoActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
