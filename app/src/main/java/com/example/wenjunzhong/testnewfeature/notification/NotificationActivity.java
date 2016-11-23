package com.example.wenjunzhong.testnewfeature.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.wenjunzhong.testnewfeature.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    private static final int BUTTON_1_NOTIFICATION_ID = 1;
    private static final int BUTTON_2_NOTIFICATION_ID = 2;
    private static final int BUTTON_3_NOTIFICATION_ID = 3;

    private static final int FOR_NOTIFICATION_ID_START = 10;

    private NotificationManager notificationManager;
    private int index = FOR_NOTIFICATION_ID_START;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean isStopService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void onClickNButton(View view) {
        switch (view.getId()) {
            case R.id.n_for_button:
                sendNotification(index);
                index++;
                break;
            case R.id.n_button_1:
                sendNotification(BUTTON_1_NOTIFICATION_ID);
                break;
            case R.id.n_button_2:
                sendNotification(BUTTON_2_NOTIFICATION_ID);
                break;
            case R.id.n_button_3:
                sendNotification(BUTTON_3_NOTIFICATION_ID);
                break;
            case R.id.n_button_4:
                Intent intent = new Intent(this, MyService.class);
                if (isStopService) {
                    stopService(intent);
                } else {
                    startService(intent);
                }
                isStopService = !isStopService;
                break;
        }
    }



    private void sendNotification(int id) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        notificationManager.notify(id, createNotify(id));
    }

    private Notification createNotify(int requestCode) {
        long time = System.currentTimeMillis();
        // PendingIntent pendingIntent = PendingIntent.getActivities(this, requestCode, null,
        // PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher))
                .setWhen(time).setContentTitle("Notification " + requestCode)
                .setContentText("Notification " + dateFormat.format(new Date(time)));
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }



}
