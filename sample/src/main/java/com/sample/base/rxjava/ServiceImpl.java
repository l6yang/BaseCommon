package com.sample.base.rxjava;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ServiceImpl {
    @FormUrlEncoded
    @POST("action.do?method=doLogin")
    Observable<String> doLogin(@Field("json_login") String json);
    @Streaming
    @GET
    Observable<ResponseBody> downloadImage(@Url String url);
}
