package com.example.wenjunzhong.testnewfeature.services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wenjunzhong.testnewfeature.BuildConfig;
import com.example.wenjunzhong.testnewfeature.R;


/**
 *
 * Created by dejun.xie on 2016/12/21.
 */
public class SystemDialogService extends Service implements View.OnClickListener {

    private boolean isInited=false;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;
    private Context mContext;
    private TextView mTvYes;
    private TextView mTvNo;
    private View mPopView;
    private AlertDialog mAlertDialog;

    public static final String LAST_POP_TIME="lastTime";//上次弹框出现的时间
    private static int TIME_INTERVAL;
    static {
        if (BuildConfig.DEBUG) {
            TIME_INTERVAL = 2 * 60 * 1000;
        } else {
            TIME_INTERVAL = 3 * 60 * 60 * 1000 * 24;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("systemDialog", "show system dialog onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = getApplicationContext();
        Log.w("systemDialog", "show system dialog onStartCommand: " + TIME_INTERVAL);
        if(overPopTime()){
//            popAlertWindow();
            showDialog();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private boolean overPopTime() {
        if(true){
            return true;
        }
        SharedPreferences sp=getSharedPreferences("sdkUpdate",MODE_PRIVATE);
        long lastTime=sp.getLong(LAST_POP_TIME,0);
        long nowTime=System.currentTimeMillis();
        if(lastTime==0){
            SharedPreferences.Editor editor=sp.edit();
            editor.putLong(LAST_POP_TIME,nowTime);
            editor.commit();
            return true;
        }else if(nowTime-lastTime>TIME_INTERVAL){
            return true;
        }
        return false;
    }

    private void popAlertWindow() {
        Log.w("systemDialog", "show system dialog start");
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type= WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED  | WindowManager.LayoutParams.FLAG_DIM_BEHIND  |
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN;
        mLayoutParams.width = 800;
        mLayoutParams.height = 400;
        mLayoutParams.gravity = Gravity.CENTER;
        mLayoutParams.dimAmount = 0.7f;
//        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.windowAnimations = android.R.style.Animation_Dialog;

        mPopView = View.inflate(mContext, R.layout.update_alert_view,null);
        initView(mPopView);
        mWindowManager.addView(mPopView,mLayoutParams);
    }

    private void initView(View v) {
        mTvYes = (TextView) v.findViewById(R.id.tv_yes);
        mTvNo = (TextView) v.findViewById(R.id.tv_no);

        mTvYes.setOnClickListener(this);
        mTvNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_yes:
                //用户点击“同意”升级
                Log.d("XDJ", "onClick: R.id.tv_yes");
                if(mWindowManager != null) {
                    mWindowManager.removeView(mPopView);
                }
                break;
            case R.id.tv_no:
                //用户“取消”升级
                Log.d("XDJ", "onClick: R.id.tv_no");
                if(mWindowManager != null) {
                    mWindowManager.removeView(mPopView);
                }
                break;
        }
        if(mAlertDialog != null){
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_Material_Light_Dialog);
        builder.setTitle(R.string.lock_screen_update);
        builder.setMessage(getString(R.string.new_version) + "\n\n" + getString(R.string.update_restart));
        builder.setPositiveButton(R.string.tip_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    mAlertDialog = null;
                }
            }
        });
        builder.setNegativeButton(R.string.tip_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.w("updateDialog", "onDismiss");
            }
        });
        mAlertDialog = builder.create();
        mAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }
}
