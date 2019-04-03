package com.loyal.base.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.loyal.base.download.DownLoadAPI;
import com.loyal.base.download.DownLoadBean;
import com.loyal.base.download.DownLoadListener;
import com.loyal.base.impl.DownloadImpl;
import com.loyal.base.notify.DownNotification;
import com.loyal.base.notify.NotifyNotification;
import com.loyal.kit.DeviceUtil;
import com.loyal.kit.ObjectUtil;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <service
 * android:name="com.loyal.base.service.DownloadService"
 * android:exported="false" />
 */
public class DownloadService extends IntentService implements DownloadImpl, DownLoadListener, Observer<InputStream> {
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    private static final String ACTION = "service.action.DownLoad";
    private static final String APKURL = "apkUrl";
    private Disposable disposable;
    private static final File apkFile = new File(SD_PATH, "update.apk");

    public DownloadService() {
        super("DownloadService");
    }

    public static void startAction(Context context, String apkUrl) {
        Intent intent = new Intent(ACTION);
        intent.setClass(context, DownloadService.class);
        intent.putExtra(APKURL, apkUrl);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (TextUtils.equals(ACTION, action)) {
                final String apkUrl = intent.getStringExtra(APKURL);
                handleAction(apkUrl);
            }
        }
    }

    private void handleAction(String apkUrl) {
        Observable<ResponseBody> observable = DownLoadAPI.getInstance(this).getDownService().downLoad(apkUrl);
        DownLoadAPI.saveFile(observable, apkFile, this);
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();
    }

    private void downloadCompleted(boolean completed, String state) {
        DownLoadBean download = new DownLoadBean();
        download.setState(completed ? "complete" : state);
        sendIntent(download);
    }

    /**
     * 界面和通知栏进度同步
     */
    private void sendIntent(DownLoadBean download) {
        String state =  ObjectUtil.replaceNull(download.getState());
        int progress = download.getProgress();
        if (TextUtils.equals("loading", state)) {
            String size = DeviceUtil.getDataSize(download.getCurrentFileSize());
            String total = DeviceUtil.getDataSize(download.getTotalFileSize());
            String current = String.format("%s / %s", size, total);
            State.UPDATE = State.UPDATE_ING;
            DownNotification.notify(this, progress, current);
        } else if (TextUtils.equals("complete", state)) {
            DownNotification.cancel(this);
            if (!apkFile.exists()) {
                State.UPDATE = State.UPDATE_FAIL;
                NotifyNotification.notify(this, "安装失败，文件不存在");
            } else {
                State.UPDATE = State.UPDATE_SUCCESS;
                DeviceUtil.install(this, apkFile);
            }
        } else {
            State.UPDATE = State.UPDATE_FAIL;
            DownNotification.cancel(this);
            NotifyNotification.notify(this, String.format("下载失败:%s", state));
        }
    }

    @Override
    public void update(long bytesRead, long contentLength, boolean done) {
        int progress = (int) (bytesRead * 100f / contentLength);
        if (progress > 0) {
            DownLoadBean download = new DownLoadBean();
            download.setTotalFileSize(contentLength);
            download.setCurrentFileSize(bytesRead);
            download.setProgress(progress);
            download.setState("loading");
            sendIntent(download);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(InputStream inputStream) {
    }

    @Override
    public void onError(Throwable e) {
        dispose();
        State.UPDATE = State.UPDATE_FAIL;
        downloadCompleted(false, e.getMessage());
    }

    @Override
    public void onComplete() {
        dispose();
        downloadCompleted(true, apkFile.getPath());
    }
}
