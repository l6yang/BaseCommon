package com.loyal.base.ui.activity;

import android.support.annotation.IntRange;
import android.support.annotation.Size;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public abstract class ABasicPerMissionActivity extends ABasicFragActivity {
    public interface OnItemPermissionListener {
        void onItemPermissionResult(@IntRange(from = 2, to = 1000) int reqCode, boolean successful);
    }

    public interface OnMultiplePermissionsListener {
        /**
         * if(successful){
         * //成功获取权限 do something
         * }else if(shouldShow){
         * //本次拒绝，下次接着弹窗提示请求
         * }else{
         * //权限申请被拒，下次也不会弹窗提示
         * }
         *
         * @param permission 多个请求的权限
         * @param successful 是否成功授予权限，true:可进行下一步操作；false：{@link Permission#shouldShowRequestPermissionRationale}
         * @param shouldShow true：本次拒绝，下次请求时；false：请求权限失败
         */
        void onMultiplePermissionsResult(String permission, boolean successful, boolean shouldShow);
    }

    /**
     * 用于单项权限申请
     */
    public void requestPermission(@IntRange(from = 2, to = 1000) final int reqCode, final OnItemPermissionListener listener, String... permissions) {
        new RxPermissions(this).request(permissions).subscribe(new Consumer<Boolean>() {
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
    public void requestMultiplePermissions(final OnMultiplePermissionsListener listener, @Size(min = 1) final String... permission) {
        new RxPermissions(this).requestEach(permission).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (null != listener)
                    listener.onMultiplePermissionsResult(permission.name, permission.granted, permission.shouldShowRequestPermissionRationale);
            }
        });
    }
}
