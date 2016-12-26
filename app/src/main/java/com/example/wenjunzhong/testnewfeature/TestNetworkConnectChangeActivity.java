package com.example.wenjunzhong.testnewfeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2016/12/20.
 */

public class TestNetworkConnectChangeActivity extends BaseActivity {

    private ConnectBrocast mBrocast = new ConnectBrocast();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mBrocast, filter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBrocast != null){
            unregisterReceiver(mBrocast);
        }
    }

    private static class ConnectBrocast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.w("TestConnectivity", "action: " + action);
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                final ConnectivityManager connectivityManager = ((ConnectivityManager) context
                        .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
                final NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();

                if (currentNetworkInfo != null) {
                    Log.w("TestConnectivity", "state: " + currentNetworkInfo.getState());
                }
            }
        }
    }

}
