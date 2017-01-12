package com.example.wenjunzhong.testnewfeature.binder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.wenjunzhong.testnewfeature.BaseActivity;
import com.example.wenjunzhong.testnewfeature.R;

/**
 * Created by wenjun.zhong on 2017/1/10.
 */

public class BinderActivity extends AppCompatActivity{
    private static final String TAG = "BinderService";
    private IBinder iReporterBinder;

    private class BinderConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iReporterBinder = service;
            Log.w(TAG, "onServiceConnected name: " + name.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iReporterBinder = null;
            Log.w(TAG, "onServiceDisconnected name: " + name.toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            View statusBarView = findViewById(R.id.status_bar_view);
            statusBarView.getLayoutParams().height = getStatusBarHeight();

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        Intent intent = new Intent(this, ReporterBinderService.class);
        bindService(intent, new BinderConnection(), BIND_AUTO_CREATE);
    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.binder_1:
                if(iReporterBinder != null){
                    reporter("this is for test string", 1);
                }
                break;
        }
    }

    private void reporter(String values, int type){
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        data.writeInterfaceToken("reporter");
        data.writeString(values);
        data.writeInt(type);

        try {
            iReporterBinder.transact(ReporterBinderService.REPORTER_CODE, data, reply, 0);
            reply.enforceInterface("reporter");
            int result = reply.readInt();
            Log.w(TAG, "remote reporter result: " + result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }finally {
            data.recycle();
            reply.recycle();
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
