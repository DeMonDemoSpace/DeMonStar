package com.cr.app.http

import com.cr.app.data.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author liuhui
 * @date 2019/12/10
 * @email 757454343@qq.com
 * @description
 */
class CommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        // 添加新的参数
        val authorizedUrlBuilder = oldRequest.url()
            .newBuilder()
            .scheme(oldRequest.url().scheme())
            .host(oldRequest.url().host())
            .addQueryParameter(Constants.KEY, Constants.KEY_VALUE)


        // 新的请求
        val newRequest = oldRequest.newBuilder()
            .method(oldRequest.method(), oldRequest.body())
            .url(authorizedUrlBuilder.build())
            .build()

        return chain.proceed(newRequest)

    }
}