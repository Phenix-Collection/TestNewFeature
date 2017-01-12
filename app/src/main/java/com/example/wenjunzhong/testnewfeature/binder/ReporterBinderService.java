package com.example.wenjunzhong.testnewfeature.binder;

import android.app.Service;
import android.content.Intent;
import android.hardware.camera2.impl.PublicKey;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wenjun.zhong on 2017/1/10.
 */

public class ReporterBinderService extends Service{
    private static final String TAG = "BinderService";

    public static final int REPORTER_CODE = 0;

    public interface IReporter{
        int reporter(String value, int type);
    }

    public final class Reporter extends Binder implements IReporter{

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){
                case REPORTER_CODE:
                    data.enforceInterface("reporter");
                    String value = data.readString();
                    int type = data.readInt();
                    int result = reporter(value, type);
                    Log.w(TAG, "onTransact value: " + value + "; type: " + type + "; result: " + result);
                    reply.writeInterfaceToken("reporter");
                    reply.writeInt(result);
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public int reporter(String value, int type) {
            Log.w(TAG, "reporter value: " + value);
            return type + 1;
        }
    }

    private Reporter mReporter;

    public ReporterBinderService(){
        mReporter = new Reporter();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mReporter;
    }
}
