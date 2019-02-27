package com.yunzhou.tdinformation.etc.ble;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yunzhou.common.utils.L;


/*
 *  @项目名：  mock500
 *  @包名：    com.yunzhou.tdinformation.etc.ble
 *  @文件名:   BluetoothLeService
 *  @创建者:   lz
 *  @创建时间:  2019/2/27 16:09
 *  @描述：
 */
public class BluetoothLeService extends Service {
    private static final String TAG = "BluetoothLeService";

    private LeBinder mLeBinder = new LeBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        L.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.d(TAG, "onBind: " + intent);
        return mLeBinder;
    }

    public String getTAG() {
        return TAG;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        L.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public class LeBinder extends Binder {
        BluetoothLeService getService() {

            return BluetoothLeService.this;
        }
    }
}
