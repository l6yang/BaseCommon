package com.loyal.base.sample.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;

import com.loyal.base.impl.OnMultiplePermissionsListener;
import com.loyal.base.sample.R;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;
import com.loyal.base.util.OutUtil;

import butterknife.ButterKnife;

public class PermissionActivity extends ABasicPerMissionActivity implements OnMultiplePermissionsListener {

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_permission;
    }

    @Override
    public void afterOnCreate() {
        multiplePermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void onMultiplePermissions(@NonNull String permission, boolean successful, boolean shouldShow) {
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
}
