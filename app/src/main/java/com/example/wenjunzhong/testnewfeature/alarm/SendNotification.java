package com.example.wenjunzhong.testnewfeature.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.wenjunzhong.testnewfeature.R;
import com.example.wenjunzhong.testnewfeature.fragments.TestFragmentActivity;


/**
 * Created by wenjun.zhong on 2016/11/11.
 */

public class SendNotification {


    public static void sendNotification(Context context) {
        NotificationManager manager =
                (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, getNotification(context.getApplicationContext()));
    }


    private static Notification getNotification(Context context) {
        Intent intent = new Intent(context, TestFragmentActivity.class);
        intent.putExtra("isToPlanFragment", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification =
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.app_name)).setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }
}
