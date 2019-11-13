package com.demon.baseframe.model;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface BaseService {

    @GET()
    Observable<String> get(@Url String url);

    @FormUrlEncoded
    @GET()
    Observable<String> get(@Url String url, @FieldMap Map<String, Object> maps);

    @POST()
    Observable<String> post(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> maps);

    @Multipart
    @POST()
    Observable<String> uploadsFile(@Url String url, @Part() MultipartBody.Part part);

    @Multipart
    @POST()
    Observable<String> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);


    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @Field("key") String s);//统一key为p，传字符串

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @Field("key") Byte[] bytes);//统一key为p，传Byte[]

}
