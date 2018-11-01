package com.loyal.base.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    public static <T> void rxExecuted(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 使用的时候需要在UI线程中更新消息
     * exp：handler，runOnUiThread(Runnable)
     */
    public static <T> void rxExecutedByIO(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}