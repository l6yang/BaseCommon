package com.sample.base.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.loyal.base.impl.DownloadImpl;
import com.loyal.base.service.DownloadService;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;
import com.loyal.kit.OutUtil;
import com.loyal.rx.impl.MultiplePermissionsListener;
import com.sample.base.FileUtil;
import com.sample.base.R;

import butterknife.ButterKnife;

public class PermissionActivity extends ABasicPerMissionActivity implements MultiplePermissionsListener {

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_permission;
    }

    @Override
    public void afterOnCreate() {
/*multiplePermissions(this, permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE,
                permission.CAMERA, permission.READ_PHONE_STATE, permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION);
        */
        multiplePermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void onMultiplePermissions(@NonNull String permission, boolean successful, boolean shouldShow) {
        if (TextUtils.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, permission)) {
            if (successful) {
                FileUtil.createFileSys();
                OutUtil.println("内存#成功获取权限");
            } else if (shouldShow)
                OutUtil.println("内存#本次拒绝，下次接着请求");
            else OutUtil.println("内存#彻底失败");
        } else if (TextUtils.equals(Manifest.permission.CAMERA, permission)) {
            if (successful) {
                OutUtil.println("相机#成功获取权限");
            } else if (shouldShow)
                OutUtil.println("相机#本次拒绝，下次接着请求");
            else OutUtil.println("相机#彻底失败");
        } else if (TextUtils.equals(Manifest.permission.READ_PHONE_STATE, permission)) {
            if (successful) {
                OutUtil.println("手机#成功获取权限");
            } else if (shouldShow)
                OutUtil.println("手机#本次拒绝，下次接着请求");
            else OutUtil.println("手机#彻底失败");
        } else if (TextUtils.equals(Manifest.permission.ACCESS_COARSE_LOCATION, permission)) {
            if (successful) {
                OutUtil.println("定位#成功获取权限");
            } else if (shouldShow)
                OutUtil.println("定位#本次拒绝，下次接着请求");
            else OutUtil.println("定位#彻底失败");
        }
        /*switch (singlePermission) {
            case Manifest.singlePermission.CAMERA:
                if (successful) {
                    OutUtil.println("相机#成功获取权限");
                } else if (shouldShow)
                    OutUtil.println("相机#本次拒绝，下次接着请求");
                else OutUtil.println("相机#彻底失败");
                break;
            case Manifest.singlePermission.READ_EXTERNAL_STORAGE:
            case Manifest.singlePermission.WRITE_EXTERNAL_STORAGE:
                if (successful) {
                    OutUtil.println("内存#成功获取权限");
                } else if (shouldShow)
                    OutUtil.println("内存#本次拒绝，下次接着请求");
                else OutUtil.println("内存#彻底失败");
                break;
        }*/


        OutUtil.println("onAllPermissions#" + permission + "   " + successful + "  " + shouldShow);
        switch (permission) {
            case Manifest.permission.CAMERA:
                if (successful) {
                    OutUtil.println("相机#成功获取权限");
                } else if (shouldShow)
                    OutUtil.println("相机#本次拒绝，下次接着请求");
                else OutUtil.println("相机#彻底失败");
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                if (successful) {
                    OutUtil.println("内存#成功获取权限");
                } else if (shouldShow)
                    OutUtil.println("内存#本次拒绝，下次接着请求");
                else OutUtil.println("内存#彻底失败");
                break;
        }
    }

    public void onClick(View view) {
        if (!TextUtils.equals(DownloadImpl.State.UPDATE_ING, DownloadImpl.State.UPDATE)) {
            DownloadImpl.State.UPDATE = DownloadImpl.State.UPDATE_ING;
            Intent intent = new Intent();
            String apkUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
            intent.putExtra("apkUrl", apkUrl);
            DownloadService.startAction(this, apkUrl);
        }
    }
}
