package com.example.wenjunzhong.testnewfeature.services;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;

/**
 *
 * Created by wenjun.zhong on 2017/7/11.
 */

public class UpdateIntentService extends IntentService {

    public UpdateIntentService() {
        super("update");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        String apkUrl = intent.getStringExtra("path");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        // sd卡文件夹、保存的文件名
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Test.apk");
        request.setTitle("Test update title");
        request.setDescription("Test update title desc");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setMimeType("application/vnd.android.package-archive");
        long downloadId = downloadManager.enqueue(request);
    }
}
