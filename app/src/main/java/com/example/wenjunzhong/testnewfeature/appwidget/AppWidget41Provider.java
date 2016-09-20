package com.example.wenjunzhong.testnewfeature.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.wenjunzhong.testnewfeature.BuildConfig;
import com.example.wenjunzhong.testnewfeature.R;

/**
 * Created by wenjun.zhong on 2016/5/6.
 */
public class AppWidget41Provider extends AppWidgetProvider {
    private static final String TAG = "AppWidget41Provider";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String APP_WIDGET_BUTTON_ACTION = "android.app.widget.button.click.ACTION";

    private static final String BUTTON_CMD_KEY = "app_widget_button_cmd_key";
    private static final byte CMD_BASE = 0;
    private static final byte CMD_POWER = CMD_BASE + 1;
    private static final byte CMD_VOL_UP = CMD_BASE + 2;
    private static final byte CMD_VOL_DOWN = CMD_BASE + 3;
    private static final byte CMD_REWIND = CMD_BASE + 4;
    private static final byte CMD_FAST_FORWARD = CMD_BASE + 5;
    private static final byte CMD_PLAY_STOP = CMD_BASE + 6;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds){
            if (DEBUG) {
                Log.d(TAG, "onUpdate: appwidgetID: " + appWidgetId);
            }

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_provider_layout);
            remoteViews.setOnClickPendingIntent(R.id.app_widget_power, getButtonPendingIntent(context, APP_WIDGET_BUTTON_ACTION, CMD_POWER));
            remoteViews.setOnClickPendingIntent(R.id.app_widget_vol_up, getButtonPendingIntent(context, APP_WIDGET_BUTTON_ACTION, CMD_VOL_UP));
            remoteViews.setOnClickPendingIntent(R.id.app_widget_vol_down, getButtonPendingIntent(context, APP_WIDGET_BUTTON_ACTION, CMD_VOL_DOWN));
            remoteViews.setOnClickPendingIntent(R.id.app_widget_rewind, getButtonPendingIntent(context, APP_WIDGET_BUTTON_ACTION, CMD_REWIND));
            remoteViews.setOnClickPendingIntent(R.id.app_widget_fast_forward, getButtonPendingIntent(context, APP_WIDGET_BUTTON_ACTION, CMD_FAST_FORWARD));
            remoteViews.setOnClickPendingIntent(R.id.app_widget_play_stop, getButtonPendingIntent(context, APP_WIDGET_BUTTON_ACTION, CMD_PLAY_STOP));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (DEBUG) {
            Log.d(TAG, "onReceive: action: " + action);
        }
        if(APP_WIDGET_BUTTON_ACTION.equals(action)){
            Toast.makeText(context, "" + intent.getByteExtra(BUTTON_CMD_KEY, CMD_BASE), Toast.LENGTH_SHORT).show();
        }
    }

    private PendingIntent getButtonPendingIntent(Context context, String action, byte cmd){
        Intent intent = new Intent(action);
        intent.putExtra(BUTTON_CMD_KEY, cmd);
        return PendingIntent.getBroadcast(context, cmd, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
