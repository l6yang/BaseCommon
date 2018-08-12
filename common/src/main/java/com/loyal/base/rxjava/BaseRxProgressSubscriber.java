package com.loyal.base.rxjava;

import android.content.Context;
import android.support.annotation.IntRange;
import android.text.TextUtils;

import com.loyal.base.rxjava.impl.ProgressCancelListener;
import com.loyal.base.rxjava.impl.ServerBaseUrlImpl;
import com.loyal.base.rxjava.impl.SubscribeCancelListener;
import com.loyal.base.rxjava.impl.SubscribeListener;
import com.loyal.base.util.ConnectUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class BaseRxProgressSubscriber<T> implements Observer<T>, ProgressCancelListener, SubscribeCancelListener, ServerBaseUrlImpl {
    private SubscribeListener<T> subscribeListener;
    private int mWhat;
    private boolean showProgressDialog;//default=false
    private Object object;
    private RxHandler.Builder builder;
    private Disposable disposable;

    public abstract void createServer(RetrofitManage retrofitManage);

    @Override
    public String httpOrHttps() {
        return "http";//default
    }

    @Override
    public String baseUrl(String clientIp) {
        return ConnectUtil.getBaseUrl(httpOrHttps(), clientIp, serverNameSpace());
    }

    public BaseRxProgressSubscriber(Context context, String ipAdd) {
        this(context, ipAdd, 1, false);
    }

    public BaseRxProgressSubscriber(Context context, String ipAdd, @IntRange(from = 1) int what, boolean showDialog) {
        initDialog(context);
        createServer(RetrofitManage.getInstance(baseUrl(ipAdd)));
        setWhat(what).showProgressDialog(showDialog);
    }

    private void initDialog(Context context) {
        builder = new RxHandler.Builder(context, this);
        setMessage("").setCancelable(true);
    }

    public BaseRxProgressSubscriber<T> setWhat(@IntRange(from = 1) int what) {
        this.mWhat = what;
        return this;
    }

    public BaseRxProgressSubscriber<T> setMessage(CharSequence sequence) {
        if (builder != null) {
            builder.setMessage(sequence);
        }
        return this;
    }

    public BaseRxProgressSubscriber<T> showProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
        return this;
    }

    public BaseRxProgressSubscriber<T> setCancelable(boolean flag) {
        if (builder != null)
            builder.setCancelable(flag);
        return this;
    }

    public BaseRxProgressSubscriber<T> setTag(Object object) {
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
        dispose();
        dismissDialog();
    }

    @Override
    public void onCancelProgress() {
        onError(new Exception("已取消操作"));
        onComplete();
    }

    /*不显示dialog，取消执行*/
    @Override
    public void onSubscribeCancel() {
        onComplete();
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();
    }
}
