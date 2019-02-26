package com.yunzhou.tdinformation.etc;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.AppManager;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/*
 *  @项目名：  mock500
 *  @包名：    com.yunzhou.tdinformation.etc
 *  @文件名:   BLEActivity
 *  @创建者:   lz
 *  @创建时间:  2019/2/26 10:23
 *  @描述：    ble探究
 */
public class BLEActivity extends BaseCommonAct implements EasyPermissions.PermissionCallbacks {
    private static final int RC_LOCATION_PERM = 0x03;
    private static final String TAG = "BLEActivity";
    private static final int SCAN_TIME = 10000;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.rv_device)
    RecyclerView mRv;
    private BluetoothAdapter mBluetoothAdapter;
    private DeviceAdapter mAdapter;
    private BtScan mBtScanCallback;
    private boolean mScaning;

    @Override
    protected int getContentView() {
        return R.layout.activity_ble;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mBtScanCallback = new BtScan(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DeviceAdapter(this);
        mRv.setAdapter(mAdapter);
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void requestLocation() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            performBLE();
        } else {
            EasyPermissions.requestPermissions(this, "我们需要您的位置以便为您提供个性化服务", RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        performBLE();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtil.showShort(this, "PermissionsDenied".concat(perms.toString()));
    }

    @OnClick({R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestLocation();
                } else {
                    performBLE();
                }
                break;
        }
    }

    private void performBLE() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (!mBluetoothAdapter.enable()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 10);
        } /*else{
            scanBlueTooth(true);
        }*/
        scanBlueTooth(true);
    }

    private void scanBlueTooth(boolean enable) {
        final BluetoothLeScanner bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            Log.e(TAG, "scanBlueTooth: bluetoothLeScanner === null" );
            return;
        }
        if (enable) {
            AppManager.getsHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScaning = false;
                    bluetoothLeScanner.stopScan(mBtScanCallback);
                }
            }, SCAN_TIME);
            mScaning = true;
            bluetoothLeScanner.startScan(mBtScanCallback);
        } else {
            mScaning = false;
            bluetoothLeScanner.stopScan(mBtScanCallback);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_CANCELED) {
            //没有开启蓝牙
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanBlueTooth(false);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BLEActivity.class);
        context.startActivity(starter);
    }

    private class BtScan extends ScanCallback {
        private final WeakReference<BLEActivity> mWeakReference;

        public BtScan(BLEActivity bleActivity) {
            mWeakReference = new WeakReference<>(bleActivity);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BLEActivity bleActivity = mWeakReference.get();
            L.d(TAG, "onLeScan: " + Thread.currentThread().getName());
            if (result.getDevice() != null && !bleActivity.mAdapter.getItems().contains(result.getDevice())) {
                bleActivity.mAdapter.addItem(result.getDevice());
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            L.d(TAG, "onBatchScanResults: " + results.toString());
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            L.e(TAG, "onScanFailed: errorCode = " + errorCode);
        }
    }
}
