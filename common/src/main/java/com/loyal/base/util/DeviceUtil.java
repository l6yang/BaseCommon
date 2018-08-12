package com.loyal.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;

public class DeviceUtil {
    public static void install(Context context, File uriFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, getProviderPath(context), uriFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProviderPath(Context context) {
        return context.getPackageName() + ".provider";
    }

    //系统版本号
    public static String deviceVersion() {
        return Build.VERSION.RELEASE;
    }

    //手机型号
    public static String deviceModel() {
        return Build.MODEL;
    }

    //手机厂商-设备品牌
    public static String deviceBrand() {
        return Build.BRAND;
    }

    /**
     * apk当前版本号
     */
    public static String apkVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // getPackageName()是你当前类的包名,0代表是获取版本信息
            return pi.versionName;
        } catch (Exception e) {
            return "1.0.0";
        }
    }

    @SuppressLint("HardwareIds")
    public static String deviceSerial() {
        return Build.SERIAL;
    }

    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F)) + "KB" : var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F)) + "MB" : "error";
    }

    public static String getSha1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte aPublicKey : publicKey) {
                String appendString = Integer.toHexString(0xFF & aPublicKey)
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
