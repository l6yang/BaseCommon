package com.loyal.base.sample.ui.activity;

import android.Manifest;

import com.loyal.base.sample.R;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;

import butterknife.ButterKnife;

public class PermissionActivity extends ABasicPerMissionActivity implements ABasicPerMissionActivity.onAllPermissionsListener {

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_permission;
    }

    @Override
    public void afterOnCreate() {
        requestAllPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
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
    public void onAllPermissionResult(String permission, boolean successful, boolean shouldShow) {
        System.out.println("onAllPermissionsResult#" + permission + "   " + successful + "  " + shouldShow);
        switch (permission) {
            case Manifest.permission.CAMERA:
                if (successful) {
                    System.out.println("相机#成功获取权限");
                } else if (shouldShow)
                    System.out.println("相机#本次拒绝，下次接着请求");
                else System.out.println("相机#彻底失败");
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                if (successful) {
                    System.out.println("内存#成功获取权限");
                } else if (shouldShow)
                    System.out.println("内存#本次拒绝，下次接着请求");
                else System.out.println("内存#彻底失败");
                break;
        }
    }
}
