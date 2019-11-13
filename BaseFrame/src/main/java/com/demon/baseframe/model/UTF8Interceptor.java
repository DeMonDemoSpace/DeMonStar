package com.demon.baseframe.model;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author DeMonnnnnn
 * @date 2019/10/8
 * @email 757454343@qq.com
 * @description
 */
public class UTF8Interceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        MediaType mediaType = request.body().contentType();
        try {
            Field field = mediaType.getClass().getDeclaredField("mediaType");
            field.setAccessible(true);
            field.set(mediaType, "application/json; charset=UTF-8");
            return chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
