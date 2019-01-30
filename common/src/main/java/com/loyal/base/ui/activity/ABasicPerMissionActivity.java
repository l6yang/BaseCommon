package com.loyal.base.ui.activity;

import android.support.annotation.IntRange;
import android.support.annotation.Size;

import com.loyal.rx.RxPermission;
import com.loyal.rx.impl.MultiplePermissionsListener;
import com.loyal.rx.impl.SinglePermissionListener;

public abstract class ABasicPerMissionActivity extends ABasicActivity {
    private RxPermission permission;

    /**
     * 用于单项权限申请
     */
    public void singlePermission(@IntRange(from = 2, to = 1000) final int reqCode, final SinglePermissionListener listener, String... permissions) {
        if (null == permission)
            permission = new RxPermission(this);
        permission.singlePermission(reqCode, listener, permissions);
    }

    /**
     * 用于多项权限申请（有点小问题）
     * 2018-09-18 10:30:00
     */
    public void multiplePermissions(final MultiplePermissionsListener listener, @Size(min = 1) final String... permissions) {
        if (null == permission)
            permission = new RxPermission(this);
        permission.multiplePermissions(listener, permissions);
    }

    @Override
    protected void onDestroy() {
        if (null != permission) {
            permission.dispose();
        }
        super.onDestroy();
    }
}
