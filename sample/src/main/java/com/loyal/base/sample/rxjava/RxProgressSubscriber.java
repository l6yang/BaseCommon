package com.loyal.base.sample.rxjava;

import android.content.Context;

import com.loyal.base.rxjava.BaseRxProgressSubscriber;
import com.loyal.base.rxjava.RetrofitManage;
import com.loyal.base.sample.impl.ServiceServer;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class RxProgressSubscriber<T> extends BaseRxProgressSubscriber<T> implements ServiceServer {
    private ServiceServer server;

    @Override
    public void createServer(RetrofitManage retrofitManage) {
        server = retrofitManage.createServer(ServiceServer.class);
    }

    @Override
    public String serverNameSpace() {
        return "mwm";
    }

    public RxProgressSubscriber(Context context, String ipAdd) {
        super(context, ipAdd);
    }

    public RxProgressSubscriber(Context context, String ipAdd, int what, boolean showDialog) {
        super(context, ipAdd, what, showDialog);
    }

    @Override
    public Observable<String> login(String account, String password) {
        return server.login(account, password);
    }

    @Override
    public Observable<String> savePhoto(String userJson, Map<String, RequestBody> mapParams) {
        return server.savePhoto(userJson, mapParams);
    }
}
