package com.sample.base.rxjava;

import android.content.Context;

import com.loyal.rx.BaseRxServerSubscriber;
import com.loyal.rx.RetrofitManage;
import com.loyal.rx.impl.RxSubscriberListener;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RxProgressSubscriber<T> extends BaseRxServerSubscriber<T> implements ServiceImpl {
    private ServiceImpl service;

    public RxProgressSubscriber(Context context) {
        super(context);
    }

    public RxProgressSubscriber(Context context, String ipAdd) {
        super(context, ipAdd);
    }

    public RxProgressSubscriber(Context context, String ipAdd, boolean showProgressDialog) {
        super(context, ipAdd, showProgressDialog);
    }

    public RxProgressSubscriber(Context context, String ipAdd, int what) {
        super(context, ipAdd, what);
    }

    public RxProgressSubscriber(Context context, String ipAdd, int what, boolean showProgressDialog, RxSubscriberListener<T> listener) {
        super(context, ipAdd, what, showProgressDialog, listener);
    }

    @Override
    public void createServer(RetrofitManage retrofitManage) {
        service = retrofitManage.createServer(ServiceImpl.class);
    }

    @Override
    public String serverNameSpace() {
        return "mwm";
    }

    @Override
    public int defaultPort() {
        return 8080;
    }

    @Override
    public Observable<String> doLogin(String json) {
        return service.doLogin(json);
    }

    @Override
    public Observable<ResponseBody> downloadImage(String url) {
        return service.downloadImage(url);
    }

}
