package com.sample.base.rxjava;

import android.graphics.Bitmap;

import io.reactivex.Observable;

public interface TaskImpl {
    /**
     * bitmap保存到存储中
     *
     * @param savePath 文件保存路径
     * @param bitmap   要保存的bitmap文件
     */
    Observable<String> saveBmp(String savePath, Bitmap bitmap);
}
