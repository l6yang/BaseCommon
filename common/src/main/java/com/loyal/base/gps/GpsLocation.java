package com.loyal.base.gps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.Window;
import android.view.WindowManager;

import com.loyal.base.impl.IBaseContacts;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class GpsLocation implements LocationListener, IBaseContacts {
    private LocationManager locationManager;
    private double latitude;//纬度
    private double longitude;//经度
    private GpsLocBean gpsLocBean = new GpsLocBean();
    private static GpsLocation mInstance;
    private WeakReference<Context> weakReference;
    private LocationListener locationListener;
    private int locationTime = 1;
    private long intervalTime = 2000;
    private boolean isStart = false;
    private int locTimer = ONCE;

    /**
     * @param context must be Activity or Application
     */
    public static GpsLocation getInstance(Context context) {
        if (mInstance == null) {
            synchronized (GpsLocation.class) {
                if (mInstance == null)
                    mInstance = new GpsLocation(context);
            }
        }
        return mInstance;
    }

    private GpsLocation(Context context) {
        weakReference = new WeakReference<>(context);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * @param locTimer 循环定位，单次定位，或者具体次数
     * @param time     定位次数，默认1次
     */
    public void locByTimes(@Times int locTimer, int time) {
        this.locTimer = locTimer;
        locationTime = time;
    }

    /**
     * @param intervalTime 定位时间间隔，单位：ms
     *                     默认2s
     */
    public void intervalTime(long intervalTime) {
        if (intervalTime < 2000)
            intervalTime = 2000;
        this.intervalTime = intervalTime;
    }

    public void start() {
        start(8);
    }

    /**
     * @param minDistance 位置刷新距离，单位：m
     */
    public void start(float minDistance) {
        if (null == locationManager) {
            isStart = false;
            return;
        }
        if (intervalTime < 1000)
            intervalTime = 1000;
        Context context = weakReference.get();
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            /*
             * 进行定位
             * provider:用于定位的locationProvider字符串:LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
             * minTime:时间更新间隔。单位：ms
             * minDistance:位置刷新距离，单位：m
             * listener:用于定位更新的监听者locationListener
             */
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                isStart = false;
                return;
            }
            List<String> providerList = locationManager.getAllProviders();
            if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                isStart = true;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, intervalTime, minDistance, this);
            } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                isStart = true;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, intervalTime, minDistance, this);
            }
        } else {
            isStart = false;
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            showAlertDialog();
        }
    }

    public void setLocationListener(LocationListener listener) {
        this.locationListener = listener;
    }

    @Override
    public void onLocationChanged(Location location) {
        getAddress(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        Context context = weakReference.get();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showAlertDialog();
            return;
        }
        if (null == locationManager) {
            isStart = false;
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        getAddress(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void getAddress(Location location) {
        Context context = weakReference.get();
        Geocoder gc = new Geocoder(context, Locale.getDefault());
        try {
            //得到纬度
            latitude = location.getLatitude();
            //得到经度
            longitude = location.getLongitude();
            List<Address> locationList = gc.getFromLocation(latitude, longitude, 1);
            Address address = locationList.get(0);//得到Address实例
            SparseArray<String> sparseArray = new SparseArray<>();
            for (int i = 0; address.getAddressLine(i) != null; i++) {
                String addressLine = address.getAddressLine(i);//得到周边信息。包含街道等。i=0，得到街道名称
                sparseArray.put(i, addressLine);
            }
            String area = replace(address.getSubLocality());
            String road = replace(address.getThoroughfare());
            gpsLocBean.setCityName(replace(address.getLocality()));
            gpsLocBean.setLatitude(address.getLatitude());
            gpsLocBean.setLongitude(address.getLongitude());
            gpsLocBean.setCountryCode(replace(address.getCountryCode()));
            gpsLocBean.setCountryName(replace(address.getCountryName()));
            gpsLocBean.setProvince(replace(address.getAdminArea()));
            gpsLocBean.setDescription(address);
            gpsLocBean.setArea(area);
            gpsLocBean.setRoad(road);
            gpsLocBean.setHasLatitude(address.hasLatitude());
            gpsLocBean.setHasLongitude(address.hasLongitude());
            gpsLocBean.setNearArray(sparseArray);
            String locAddress = String.format("%s%s", replace(address.getAddressLine(0)), replace(address.getAddressLine(1)));
            gpsLocBean.setAddress(locAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != locationListener)
            locationListener.onLocation(gpsLocBean);
        switch (locTimer) {
            case LOOP:
                break;
            case ONCE:
                stop();
                break;
            case MORE:
                if (1 == locationTime) {
                    stop();
                } else locationTime--;
                break;
        }
    }

    public String getWd() {
        return String.valueOf(latitude);
    }

    public String getJd() {
        return String.valueOf(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isStart() {
        return isStart;
    }

    public String getAddress() {
        return gpsLocBean.getAddress();
    }

    public void stop() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            locationManager = null;
        }
        isStart = false;
    }

    private String replace(CharSequence sequence) {
        return BaseStr.replaceNull(sequence);
    }

    public interface LocationListener {
        void onLocation(GpsLocBean gpsLocBean);
    }

    public static final int LOOP = 0x00000000;
    public static final int ONCE = 0x00000001;
    public static final int MORE = 0x00000002;

    @IntDef({LOOP, ONCE, MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Times {
    }

    /**
     * <!-- 非Activity中弹Dialog -->
     * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
     */
    private void showAlertDialog() {
        final Context context = weakReference.get();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示")
                .setMessage("无法定位，请打开定位服务")
                .setCancelable(false)
                .setPositiveButton("确 定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                .setNegativeButton("取 消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                System.exit(1);
                            }
                        });
        AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (null != window)
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
