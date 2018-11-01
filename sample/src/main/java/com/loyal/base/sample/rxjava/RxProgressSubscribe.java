package com.loyal.base.sample.rxjava;

import android.content.Context;

import com.loyal.base.rxjava.BaseRxServerSubscriber;
import com.loyal.base.rxjava.RetrofitManage;

public class RxProgressSubscribe<T > extends BaseRxServerSubscriber<T> {
    public RxProgressSubscribe(Context context, String ipAdd) {
        super(context, ipAdd);
    }

    public RxProgressSubscribe(Context context, String ipAdd, int what) {
        super(context, ipAdd, what);
    }

    public RxProgressSubscribe(Context context, String ipAdd, boolean showDialog) {
        super(context, ipAdd, showDialog);
    }

    public RxProgressSubscribe(Context context, String ipAdd, int what, boolean showDialog) {
        super(context, ipAdd, what, showDialog);
    }

    @Override
    public void createServer(RetrofitManage retrofitManage) {

    }

    @Override
    public String serverNameSpace() {
        return "mwm";
    }

    @Override
    public String defaultPort() {
        return "8080";
    }

    @Override
    public String baseUrl(String clientIp) {
        String ipAdd=super.baseUrl(clientIp);
        System.out.println("baseUrl://"+ipAdd);
        return super.baseUrl(clientIp);
    }
}
