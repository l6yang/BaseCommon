package com.loyal.base.sample.ui.activity;

import android.Manifest;
import android.Manifest.permission;
import android.text.TextUtils;

import com.loyal.base.impl.OnMultiplePermissionsListener;
import com.loyal.base.sample.R;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;

import butterknife.ButterKnife;

public class AllPermissionActivity extends ABasicPerMissionActivity implements  OnMultiplePermissionsListener {

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_all_permission;
    }

    @Override
    public void afterOnCreate() {
        /*allPermissions(this, singlePermission.WRITE_EXTERNAL_STORAGE,singlePermission.READ_EXTERNAL_STORAGE,
                singlePermission.READ_PHONE_STATE,singlePermission.CAMERA,singlePermission.ACCESS_COARSE_LOCATION,singlePermission.ACCESS_FINE_LOCATION);
    */
        multiplePermissions(this, permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE,
                permission.CAMERA, permission.READ_PHONE_STATE, permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION);

    }

    @Override
    public boolean isTransStatus() {
        return false;
    }

    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void onMultiplePermissions(String permission, boolean successful, boolean shouldShow) {
        if (TextUtils.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, permission)) {
            if (successful) {
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
}
