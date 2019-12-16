package com.cr.app.http

import com.cr.app.BuildConfig
import com.demon.baseframe.model.BaseApi
import retrofit2.Retrofit

/**
 * @author liuhui
 * @date 2019/12/3
 * @email 757454343@qq.com
 * @description
 */
class Api {
    companion object {

        fun getRetrofit(base_url: String): Retrofit? {
            val baseApi = BaseApi().setLog(BuildConfig.DEBUG).setCache(false)
            return baseApi.getRetrofit(base_url, CommonInterceptor())
        }
    }

}