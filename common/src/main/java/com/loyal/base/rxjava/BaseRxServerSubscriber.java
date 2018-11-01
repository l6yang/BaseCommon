package com.loyal.base.rxjava;

import android.content.Context;
import android.support.annotation.IntRange;
import android.text.TextUtils;

import com.loyal.base.rxjava.impl.ProgressCancelListener;
import com.loyal.base.rxjava.impl.ServerBaseUrlImpl;
import com.loyal.base.rxjava.impl.UnSubscribeListener;
import com.loyal.base.rxjava.impl.SubscribeListener;
import com.loyal.base.util.ConnectUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 适用于执行Retrofit+RxJava与服务器交互
 */
public abstract class BaseRxServerSubscriber<T> implements Observer<T>, ProgressCancelListener, UnSubscribeListener, ServerBaseUrlImpl {
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
    public String defaultPort() {
        return "9080";
    }

    @Override
    public String baseUrl(String clientIp) {
        return ConnectUtil.getBaseUrl(httpOrHttps(), clientIp, defaultPort(), serverNameSpace());
    }

    public BaseRxServerSubscriber(Context context, String ipAdd) {
        this(context, ipAdd, 2);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd, @IntRange(from = 2) int what) {
        this(context, ipAdd, what, false);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd, boolean showDialog) {
        this(context, ipAdd, 2, false);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd, @IntRange(from = 2) int what, boolean showDialog) {
        initDialog(context);
        createServer(RetrofitManage.getInstance(baseUrl(ipAdd)));
        setWhat(what).showProgressDialog(showDialog);
    }

    private void initDialog(Context context) {
        builder = new RxHandler.Builder(context, this);
        setMessage("").setCancelable(true).setCanceledOnTouchOutside(true);
    }

    public BaseRxServerSubscriber<T> setWhat(@IntRange(from = 2) int what) {
        this.mWhat = what;
        return this;
    }

    public BaseRxServerSubscriber<T> setMessage(CharSequence sequence) {
        if (builder != null) {
            builder.setMessage(sequence);
        }
        return this;
    }

    public BaseRxServerSubscriber<T> showProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
        return this;
    }

    public BaseRxServerSubscriber<T> setTag(Object object) {
        this.object = object;
        return this;
    }

    public BaseRxServerSubscriber<T> setCancelable(boolean cancelable) {
        if (builder != null)
            builder.setCancelable(cancelable);
        return this;
    }

    public void setCanceledOnTouchOutside(boolean flag) {
        if (builder != null)
            builder.setCanceledOnTouchOutside(flag);
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
        onUnsubscribe();
    }

    /**
     * 不显示dialog，取消执行/订阅
     */
    @Override
    public void onUnsubscribe() {
        onComplete();
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();
    }
}
