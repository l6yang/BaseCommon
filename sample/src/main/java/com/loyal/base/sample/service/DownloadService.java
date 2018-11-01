package com.loyal.base.sample.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.loyal.base.download.DownLoadAPI;
import com.loyal.base.download.DownLoadBean;
import com.loyal.base.download.DownLoadListener;
import com.loyal.base.impl.IBaseContacts;
import com.loyal.base.sample.FileUtil;
import com.loyal.base.sample.notify.DownNotification;
import com.loyal.base.sample.notify.NotifyNotification;
import com.loyal.base.util.DeviceUtil;
import com.loyal.base.util.OutUtil;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownloadService extends IntentService implements IBaseContacts, DownLoadListener, Observer<InputStream> {
    private static final String ACTION = "service.action.Download";
    private Disposable disposable;

    private final File file = new File(FileUtil.apkPath, FileUtil.apkName);

    public DownloadService() {
        super("CheckUpdateService");
    }

    public static void startAction(Context context, Intent intent) {
        intent.setClass(context, DownloadService.class);
        intent.setAction(ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION.equals(action)) {
                final String apkUrl = intent.getStringExtra("apkUrl");
                handleAction(apkUrl);
            }
        }
    }

    private void handleAction(String apkUrl) {
        Observable<ResponseBody> observable = DownLoadAPI.getInstance(this).getDownService().downLoad(apkUrl);
        DownLoadAPI.saveFile(observable, file, this);
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
        /*Intent intent = new Intent(ActionImpl.DOWNLOAD);
        intent.putExtra("downLoad", download);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);*/

        String state = IBaseContacts.BaseStr.replaceNull(download.getState());
        int progress = download.getProgress();
        OutUtil.println("已下载进度 " + progress + "%");
        if (TextUtils.equals("loading", state)) {
            String size = DeviceUtil.getDataSize(download.getCurrentFileSize());
            String total = DeviceUtil.getDataSize(download.getTotalFileSize());
            String current = String.format("%s / %s", size, total);
            DownNotification.notify(this, progress, current);
        } else if (TextUtils.equals("complete", state)) {
            DownNotification.cancel(this);
            if (!file.exists()) {
                NotifyNotification.notify(this, "安装失败，文件不存在");
            } else {
                DeviceUtil.install(this, file);
            }
        } else {
            DownNotification.cancel(this);
            NotifyNotification.notify(this, "下载失败");
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
        OutUtil.println("onNext::");
    }

    @Override
    public void onError(Throwable e) {
        dispose();
        downloadCompleted(false, e.getMessage());
    }

    @Override
    public void onComplete() {
        dispose();
        OutUtil.println("onComplete");
        downloadCompleted(true, file.getPath());
    }
}
