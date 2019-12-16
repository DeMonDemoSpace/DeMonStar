package com.demon.baseframe.model;


import com.demon.baseframe.app.BaseApp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by DeMon on 2017/9/18.
 */

public class BaseApi {
    //超时时长，单位：毫秒
    private int TimeOut = 7676;
    private boolean isCache = false;
    private boolean isLog = true;

    public BaseApi setTimeOut(int timeOut) {
        TimeOut = timeOut;
        return this;
    }

    public BaseApi setCache(boolean cache) {
        isCache = cache;
        return this;
    }

    public BaseApi setLog(boolean log) {
        isLog = log;
        return this;
    }

    /**
     * 使用OkHttp配置了超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @param interceptors 自定义的拦截器
     * @return
     */
    public Retrofit getRetrofit(String baseUrl, Interceptor... interceptors) {
        //创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.readTimeout(TimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(TimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(TimeOut, TimeUnit.MILLISECONDS);
        if (isCache) {
            //缓存
            File cacheFile = new File(BaseApp.getContext().getCacheDir(), "cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 200); //200Mb
            builder.addInterceptor(new CacheInterceptor())//缓存
                    .addNetworkInterceptor(new CacheInterceptor())//网络缓存
                    .cache(cache);
        }
        if (isLog) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);//日志
        }

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0
                .build();
        return retrofit;
    }


}


