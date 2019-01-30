package com.sample.base.rxjava;

import android.content.Context;

import com.loyal.rx.BaseRxServerSubscriber;
import com.loyal.rx.RetrofitManage;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class RxServerSubscriber<T> extends BaseRxServerSubscriber<T> implements ServiceServer {
    private ServiceServer server;

    @Override
    public void createServer(RetrofitManage retrofitManage) {
        server = retrofitManage.createServer(ServiceServer.class);
    }

    @Override
    public String serverNameSpace() {
        return "mwm";
    }

    public RxServerSubscriber(Context context, String ipAdd) {
        super(context, ipAdd);
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
