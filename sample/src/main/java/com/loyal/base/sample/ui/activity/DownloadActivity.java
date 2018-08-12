package com.loyal.base.sample.ui.activity;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.loyal.base.impl.OnMultiplePermissionsListener;
import com.loyal.base.sample.FileUtil;
import com.loyal.base.sample.R;
import com.loyal.base.sample.notify.UpdateNotification;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;

import butterknife.ButterKnife;

public class DownloadActivity extends ABasicPerMissionActivity implements OnMultiplePermissionsListener {

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_download;
    }

    @Override
    public void afterOnCreate() {
        /*multiplePermissions(this, permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE,
                permission.CAMERA, permission.READ_PHONE_STATE, permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION);
        */
        multiplePermissions(this, permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void onMultiplePermissions(String permission, boolean successful, boolean shouldShow) {
        if (TextUtils.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, permission)) {
            if (successful) {
                FileUtil.createFileSys();
                System.out.println("内存#成功获取权限");
            } else if (shouldShow)
                System.out.println("内存#本次拒绝，下次接着请求");
            else System.out.println("内存#彻底失败");
        } else if (TextUtils.equals(Manifest.permission.CAMERA, permission)) {
            if (successful) {
                System.out.println("相机#成功获取权限");
            } else if (shouldShow)
                System.out.println("相机#本次拒绝，下次接着请求");
            else System.out.println("相机#彻底失败");
        } else if (TextUtils.equals(Manifest.permission.READ_PHONE_STATE, permission)) {
            if (successful) {
                System.out.println("手机#成功获取权限");
            } else if (shouldShow)
                System.out.println("手机#本次拒绝，下次接着请求");
            else System.out.println("手机#彻底失败");
        } else if (TextUtils.equals(Manifest.permission.ACCESS_COARSE_LOCATION, permission)) {
            if (successful) {
                System.out.println("定位#成功获取权限");
            } else if (shouldShow)
                System.out.println("定位#本次拒绝，下次接着请求");
            else System.out.println("定位#彻底失败");
        }
        /*switch (singlePermission) {
            case Manifest.singlePermission.CAMERA:
                if (successful) {
                    System.out.println("相机#成功获取权限");
                } else if (shouldShow)
                    System.out.println("相机#本次拒绝，下次接着请求");
                else System.out.println("相机#彻底失败");
                break;
            case Manifest.singlePermission.READ_EXTERNAL_STORAGE:
            case Manifest.singlePermission.WRITE_EXTERNAL_STORAGE:
                if (successful) {
                    System.out.println("内存#成功获取权限");
                } else if (shouldShow)
                    System.out.println("内存#本次拒绝，下次接着请求");
                else System.out.println("内存#彻底失败");
                break;
        }*/
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        String apkUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
        intent.putExtra("apkUrl", apkUrl);
        UpdateNotification.notify(this, intent);
    }
}
