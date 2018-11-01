package com.loyal.base.rxjava;

import android.support.annotation.IntRange;

import com.loyal.base.rxjava.impl.RxSubscriberListener;

public interface RxListener<T> {
    BaseRxSubscriber<T> setWhat(@IntRange(from = 2, to = 1000) int what);

    BaseRxSubscriber<T> showProgressDialog(boolean showProgressDialog);

    BaseRxSubscriber<T> setDialogMessage(CharSequence message);

    BaseRxSubscriber<T> setTag(Object objTag);

    BaseRxSubscriber<T> setCancelable(boolean cancelable);

    void setCanceledOnTouchOutside(boolean flag);

    void setSubscribeListener(RxSubscriberListener<T> listener);

    void dispose();
}
