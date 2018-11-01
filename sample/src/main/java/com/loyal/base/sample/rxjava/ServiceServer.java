package com.loyal.base.sample.rxjava;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ServiceServer {
    @FormUrlEncoded
    @POST("action.do?method=" + "testLogin")
    Observable<String> login(@Field("account") String account, @Field("password") String password);

    /**
     * @param mapParams requestBodyMap.put("file" + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
     */
    @Multipart
    @POST("action.do?method=" + "savePhoto")
    Observable<String> savePhoto(@Part("userJson") String userJson, @PartMap Map<String, RequestBody> mapParams);

}
