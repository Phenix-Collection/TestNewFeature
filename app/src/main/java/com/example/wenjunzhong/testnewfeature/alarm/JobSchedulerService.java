package com.example.wenjunzhong.testnewfeature.alarm;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2016/11/11.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w(AlarmManagerUtil.TAG, "onStartJob");
        SendNotification.sendNotification(getApplicationContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(AlarmManagerUtil.TAG, "onStopJob");
        return false;
    }
}
