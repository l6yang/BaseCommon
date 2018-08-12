package com.loyal.base.impl;

import com.tbruyelle.rxpermissions2.Permission;

public interface OnMultiplePermissionsListener {
    /**
     * if(successful){
     * // `singlePermission.name` is granted !
     * //成功获取权限 do something
     * }else if(shouldShow){
     *
     * //本次拒绝，下次接着弹窗提示请求
     * }else{
     * //权限申请被拒，下次也不会弹窗提示
     * }
     *
     * @param permission 权限名称
     * @param successful 是否成功授予权限，true:可进行下一步操作；false：{@link Permission#shouldShowRequestPermissionRationale}
     * @param shouldShow true：本次拒绝，下次请求时；false：请求权限失败
     */
    void onMultiplePermissions(String permission, boolean successful, boolean shouldShow);
}