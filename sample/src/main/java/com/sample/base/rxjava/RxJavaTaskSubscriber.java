package com.sample.base.rxjava;

import android.content.Context;
import android.graphics.Bitmap;

import com.sample.base.ImageUtil;
import com.loyal.rx.BaseRxSubscriber;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RxJavaTaskSubscriber<T> extends BaseRxSubscriber<T> implements TaskImpl {

    public RxJavaTaskSubscriber(Context context) {
        super(context);
    }

    @Override
    public Observable<String> saveBmp(final String savePath, final Bitmap bitmap) {
        return Observable.just(savePath).
                map(new Function<String, String>() {
                    @Override
                    public String apply(String path) {
                        return ImageUtil.saveBitmap(savePath, bitmap);
                    }
                });
    }
}

