package com.loyal.base.ui.fragment;

import android.support.annotation.IntRange;
import android.support.annotation.Size;
import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public abstract class ABasicPerMissionFragment extends ABasicFragment {
    public interface onItemPermissionListener {
        void onItemPermissionResult(@IntRange(from = 2) int reqCode, boolean successful);
    }

    public interface onAllPermissionsListener {
        void onAllPermissionsResult(String permission, boolean successful, boolean shouldShow);
    }

    /**
     * 用于单项权限申请
     */
    public void requestPermission(@IntRange(from = 2) final int reqCode, final onItemPermissionListener listener, String... permissions) {
        FragmentActivity activity = getActivity();
        if (null == activity)
            return;
        new RxPermissions(activity).request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (null != listener)
                    listener.onItemPermissionResult(reqCode, aBoolean);
            }
        });
    }

    /**
     * 用于多项权限申请
     */
    public void requestAllPermissions(final onAllPermissionsListener listener, @Size(min = 1) final String... permission) {
        FragmentActivity activity = getActivity();
        if (null == activity)
            return;
        new RxPermissions(activity).requestEach(permission).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (null != listener)
                    listener.onAllPermissionsResult(permission.name, permission.granted, permission.shouldShowRequestPermissionRationale);
            }
        });
    }
}
