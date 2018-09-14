package com.loyal.base.sample.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.loyal.base.rxjava.RxUtil;
import com.loyal.base.rxjava.impl.SubscribeListener;
import com.loyal.base.sample.beans.ResultBean;
import com.loyal.base.sample.beans.UpdateBean;
import com.loyal.base.sample.notify.NotifyNotification;
import com.loyal.base.sample.notify.UpdateNotification;
import com.loyal.base.sample.rxjava.RxServerUnSubscribe;
import com.loyal.base.util.ConnectUtil;
import com.loyal.base.util.DeviceUtil;
import com.loyal.base.util.GsonUtil;

public class CheckUpdateService extends IntentService implements SubscribeListener<String> {
    private static final String ACTION = "service.action.CheckUpdate";

    private static final String EXTRA_PARAM1 = "service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "service.extra.PARAM2";

    public CheckUpdateService() {
        super("CheckUpdateService");
    }

    public static void startAction(Context context, String ipAdd, String notify) {
        Intent intent = new Intent(context, CheckUpdateService.class);
        intent.setAction(ACTION);
        intent.putExtra(EXTRA_PARAM1, ipAdd);
        intent.putExtra(EXTRA_PARAM2, notify);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION.equals(action)) {
                handleAction(intent);
            }
        }
    }

    private void handleAction(Intent intent) {
        String ipAdd = intent.getStringExtra(EXTRA_PARAM1);
        RxServerUnSubscribe<String> subscriber = new RxServerUnSubscribe<>(this, ipAdd);
        subscriber.showProgressDialog(false);
        subscriber.setTag(intent).setSubscribeListener(this);
        String sbsn = DeviceUtil.deviceSerial();
        String apkVersion = DeviceUtil.apkVersion(this);
        RxUtil.rxExecuted(subscriber.login(sbsn, apkVersion), subscriber);
    }

    @Override
    public void onResult(int what, Object tag, String result) {
        System.out.println("check  onResult：" + result);
        try {
            Intent intent = (Intent) tag;
            String notify = intent.getStringExtra(EXTRA_PARAM2);
            ResultBean<UpdateBean> resultBean = (ResultBean<UpdateBean>) GsonUtil.json2BeanObject(result, ResultBean.class, UpdateBean.class);
            String code = resultBean.getCode();
            String message = resultBean.getMessage();
            UpdateBean updateBean = resultBean.getObj();
            if (TextUtils.equals("200", code)) {
                String apkUrl = updateBean.getUrl();
                intent.putExtra("apkUrl", apkUrl);
                UpdateNotification.notify(this, intent);
            } else {
                if (TextUtils.equals("settings", notify))
                    NotifyNotification.notify(this, TextUtils.isEmpty(message)?"暂无新版本":message);
            }
        } catch (Exception e) {
            onError(what, tag, e);
        }
    }

    @Override
    public void onError(int what, Object tag, Throwable e) {
        System.out.println("更新失败:" + e);
        Intent intent = (Intent) tag;
        String notify = intent.getStringExtra(EXTRA_PARAM2);
        if (TextUtils.equals("setting", notify))
            NotifyNotification.notify(this, "检测更新失败" + ConnectUtil.getError(e));
    }
}
