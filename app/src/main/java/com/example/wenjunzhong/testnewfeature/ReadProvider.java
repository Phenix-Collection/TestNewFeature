package com.example.wenjunzhong.testnewfeature;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2017/1/6.
 */

public class ReadProvider {

    public static void readDB(Context context){
        Uri uriStr = Uri.parse("content://com.tct.launcher.settings/favorites?notify=true");
        Cursor cursor = context.getContentResolver().query(uriStr, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String intent = cursor.getString(cursor.getColumnIndex("intent"));
                int container = cursor.getInt(cursor.getColumnIndex("container"));
                int screen = cursor.getInt(cursor.getColumnIndex("screen"));
                int cellX = cursor.getInt(cursor.getColumnIndex("cellX"));
                int cellY = cursor.getInt(cursor.getColumnIndex("cellY"));
                int spanX = cursor.getInt(cursor.getColumnIndex("spanX"));
                int spanY = cursor.getInt(cursor.getColumnIndex("spanY"));
                int itemType = cursor.getInt(cursor.getColumnIndex("itemType"));
                int appWidgetId = cursor.getInt(cursor.getColumnIndex("appWidgetId"));
                int isShortcut = cursor.getInt(cursor.getColumnIndex("isShortcut"));
                int iconType = cursor.getInt(cursor.getColumnIndex("iconType"));

                String iconPackage = cursor.getString(cursor.getColumnIndex("iconPackage"));
                String iconResource = cursor.getString(cursor.getColumnIndex("iconResource"));
                String uri = cursor.getString(cursor.getColumnIndex("uri"));
                int displayMode = cursor.getInt(cursor.getColumnIndex("displayMode"));
                String appWidgetProvider = cursor.getString(cursor.getColumnIndex("appWidgetProvider"));
                int modified = cursor.getInt(cursor.getColumnIndex("modified"));
                int restored = cursor.getInt(cursor.getColumnIndex("restored"));
                int profileId = cursor.getInt(cursor.getColumnIndex("profileId"));
                int rank = cursor.getInt(cursor.getColumnIndex("rank"));
                int options = cursor.getInt(cursor.getColumnIndex("options"));

                Log.w("testProvide", "_id:"  + _id  + ", title:" + title + ", intent:" +  intent + ", container:" + container + ", screen:" + screen + ", cellX:" + cellX + ", cellY:" + cellY
                 + ", spanX:" + spanX + ", spanY:" + spanY + ", itemType:" + itemType+ ", appWidgetId:" + appWidgetId + ", isShortcut:" + isShortcut+ ", iconType:" + iconType+ ", iconPackage:" + iconPackage
                        + ", iconResource:" + iconResource+ ", uri:" + uri+ ", displayMode:" + displayMode+ ", appWidgetProvider:" + appWidgetProvider+ ", modified;" + modified+ ", restored;" + restored
                        + ", profileId:" + profileId+ ", rank:" + rank+ ", options:" + options);
            }
        }
    }
}
