package com.yunzhou.tdinformation.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.lang.ref.WeakReference;
import java.util.List;

/*
 *  @项目名：  mio_sport
 *  @包名：    com.lifesense.mio.map.location
 *  @文件名:   GPSLocationManager
 *  @创建者:   lz
 *  @创建时间:  2018/12/21 19:38
 *  @描述：
 */
public class GPSLocationManager {

    private final WeakReference<Context> mContextWeakReference;
    private static GPSLocationManager sGPSLocationManager;
    private LocationManager mLocationManager;

    public static GPSLocationManager getInstance(Context context) {
        if (sGPSLocationManager == null)
            synchronized (GPSLocationManager.class) {
                if (sGPSLocationManager == null)
                    sGPSLocationManager = new GPSLocationManager(context);
            }

        return sGPSLocationManager;
    }

    private GPSLocationManager(Context context) {
        mContextWeakReference = new WeakReference<>(context);
        if (mContextWeakReference.get() != null) {
            mLocationManager = (LocationManager) mContextWeakReference.get().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLoction() {
        List<String> providers = mLocationManager.getProviders(true);
        if (ActivityCompat.checkSelfPermission(mContextWeakReference.get(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContextWeakReference.get(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location;
            }
        }
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                return location;
            }
        }
        return null;
    }
}
