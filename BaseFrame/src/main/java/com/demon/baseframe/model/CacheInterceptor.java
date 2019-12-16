package com.demon.baseframe.model;

import com.demon.baseframe.app.BaseApp;
import com.demon.baseutil.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description 云端响应头拦截器，用来配置缓存策略
 */
public class CacheInterceptor implements Interceptor {
    /**
     * 设缓存有效期为7天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 7;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetWorkUtils.isNetConnected(BaseApp.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isNetConnected(BaseApp.getContext())) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public,max-age" + 0)
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .build();
        }
    }
}