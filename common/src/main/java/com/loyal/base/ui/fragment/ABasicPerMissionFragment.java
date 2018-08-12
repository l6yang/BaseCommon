package com.loyal.base.ui.fragment;

import android.support.annotation.IntRange;
import android.support.annotation.Size;

import com.loyal.base.impl.OnMultiplePermissionsListener;
import com.loyal.base.impl.OnSinglePermissionListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class ABasicPerMissionFragment extends ABasicFragment {
    private RxPermissions rxPermissions;
    private Disposable disposable;

    /**
     * 用于单项权限申请
     */
    public void singlePermission(@IntRange(from = 2, to = 1000) final int reqCode, final OnSinglePermissionListener listener, String... permissions) {
        if (null == rxPermissions)
            rxPermissions = new RxPermissions(this);
        disposable = rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if (null != listener)
                    listener.onSinglePermission(reqCode, aBoolean);
            }
        });
    }

    /**
     * 用于多项权限申请
     */
    public void multiplePermissions(final OnMultiplePermissionsListener listener, @Size(min = 1) final String... permission) {
        if (null == rxPermissions)
            rxPermissions = new RxPermissions(this);
        disposable = rxPermissions.requestEach(permission).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) {
                if (null != listener)
                    listener.onMultiplePermissions(permission.name, permission.granted, permission.shouldShowRequestPermissionRationale);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
