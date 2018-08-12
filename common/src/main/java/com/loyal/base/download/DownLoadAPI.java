package com.loyal.base.download;

import com.loyal.base.impl.IBaseContacts;
import com.loyal.base.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public class DownLoadAPI implements IBaseContacts {
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit.Builder retrofit;

    public static DownLoadAPI getInstance(DownLoadListener listener) {
        return new DownLoadAPI(listener);
    }

    private DownLoadAPI(DownLoadListener listener) {
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseStr.baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public DownloadService getDownService() {
        return retrofit.build().create(DownloadService.class);
    }

    public static void saveFile(Observable<ResponseBody> observable, final File file, Observer<InputStream> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                         @Override
                         public InputStream apply(@NonNull ResponseBody responseBody) throws Exception {
                             return responseBody.byteStream();
                         }
                     }
                ).observeOn(Schedulers.computation())
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(@NonNull InputStream inputStream) throws Exception {
                        writeFile(inputStream, file);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void writeFile(InputStream in, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 128];
            int len;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeStream(fos);
            IOUtil.closeStream(in);
        }
    }

    public interface DownloadService {

        @Streaming
        @GET
        Observable<ResponseBody> downLoad(@Url String url);
    }
}