package com.loyal.base.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeviceUtil {
    /**
     * 安装程序
     *
     * @param uriFile 安装文件存放路径
     */
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

    /**
     * 卸载app
     *
     * @param packageName 要卸载程序的包名
     */
    public static void uninstall(Activity context, String packageName) {
        try {
            //通过程序的包名创建URI
            Uri packageURI = Uri.parse("package:" + packageName);
            //创建Intent意图
            Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
            //执行卸载程序
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据包名判断程序是否安装
     */
    public static boolean isAvailable(Context context, String packageName) {
        try {// 获取packageManager
            final PackageManager packageManager = context.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            // 用于存储所有已安装程序的包名
            List<String> packageNames = new ArrayList<>();
            // 从pinfo中将包名字逐一取出，压入pName list中
            if (packageInfos != null) {
                for (int i = 0; i < packageInfos.size(); i++) {
                    String packName = packageInfos.get(i).packageName;
                    packageNames.add(packName);
                }
            }
            // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
            return packageNames.contains(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文件判断是否安装了指定的软件
     */
    public static boolean isAvailable(Context context, File file) {
        try {
            return !TextUtils.isEmpty(getPackageName(context, file.getAbsolutePath())) && isAvailable(context, getPackageName(context, file.getAbsolutePath()));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据文件路径获取包名
     *
     * @param filePath 文件路径
     */
    public static String getPackageName(Context context, String filePath) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                ApplicationInfo appInfo = info.applicationInfo;
                return appInfo.packageName;  //得到安装包名称
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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
            e.printStackTrace();
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

    /**
     * 获取设备分辨率(获取带虚拟按键手机的屏幕分辨率)
     **/
    public static String getDisplay(Context context) {
        return getDisplayWidth(context) + "*" + getDisplayHeight(context);
    }

    public static int getDisplayWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null == manager)
            return -1;
        Display display = manager.getDefaultDisplay();
        display.getMetrics(metrics);
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            return dm.widthPixels;
        } catch (Exception e) {
            return metrics.widthPixels;
        }
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null == manager)
            return -1;
        Display display = manager.getDefaultDisplay();
        display.getMetrics(metrics);
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            return dm.heightPixels;
        } catch (Exception e) {
            return metrics.heightPixels;
        }
    }
}
