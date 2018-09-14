package com.loyal.base.rxjava;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.loyal.base.rxjava.impl.ProgressCancelListener;
import com.loyal.base.rxjava.impl.UnSubscribeListener;
import com.loyal.base.rxjava.impl.SubscribeListener;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseRxTaskSubscriber<T> implements Observer<T>, ProgressCancelListener, UnSubscribeListener {
    private SubscribeListener<T> subscribeListener;
    private int mWhat;
    private boolean showProgressDialog;//default=false
    private Object object;
    private RxHandler.Builder builder;
    private Disposable disposable;

    public BaseRxTaskSubscriber(Context context) {
        this(context, true);
    }

    public BaseRxTaskSubscriber(Context context, boolean showDialog) {
        this(context, 2, showDialog);
    }

    public BaseRxTaskSubscriber(Context context, @IntRange(from = 2) int what, boolean showDialog) {
        initDialog(context);
        setWhat(what).showProgressDialog(showDialog);
    }

    private void initDialog(Context context) {
        builder = new RxHandler.Builder(context, this);
        setMessage("").setCancelable(true).setCanceledOnTouchOutside(true);
    }

    public BaseRxTaskSubscriber<T> setWhat(@IntRange(from = 2) int what) {
        this.mWhat = what;
        return this;
    }

    public BaseRxTaskSubscriber<T> setMessage(CharSequence sequence) {
        if (builder != null) {
            builder.setMessage(sequence);
        }
        return this;
    }

    public BaseRxTaskSubscriber<T> showProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
        return this;
    }

    public BaseRxTaskSubscriber<T> setCancelable(boolean flag) {
        if (builder != null)
            builder.setCancelable(flag);
        return this;
    }

    public BaseRxTaskSubscriber<T> setCanceledOnTouchOutside(boolean flag) {
        if (builder != null)
            builder.setCanceledOnTouchOutside(flag);
        return this;
    }

    public BaseRxTaskSubscriber<T> setTag(Object object) {
        this.object = object;
        return this;
    }

    public void setSubscribeListener(SubscribeListener<T> listener) {
        this.subscribeListener = listener;
    }

    private void showDialog() {
        if (showProgressDialog && null != builder) {
            builder.show();
        }
    }

    private void dismissDialog() {
        if (null != builder) {
            builder.dismiss();
            builder = null;
        }
        showProgressDialog = false;
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.disposable = disposable;
        showDialog();
    }

    @Override
    public void onNext(T result) {
        if (null != subscribeListener)
            subscribeListener.onResult(mWhat, object, result);
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        if (TextUtils.equals("已取消操作", null == e ? "" : e.getMessage())) {
            if (null != subscribeListener)
                subscribeListener.onError(mWhat, object, e);
            subscribeListener = null;
        } else {
            if (null != subscribeListener)
                subscribeListener.onError(mWhat, object, e);
        }
        dispose();
    }

    @Override
    public void onComplete() {
        dismissDialog();
        dispose();
    }

    @Override
    public void onCancelProgress() {
        onError(new Exception("已取消操作"));
        onComplete();
    }

    /*不显示dialog，取消执行*/
    @Override
    public void onUnsubscribe() {
        onComplete();
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();
    }
}

