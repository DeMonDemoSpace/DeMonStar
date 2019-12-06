package com.cr.app.http

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

        @Volatile
        private var retrofit: Retrofit? = null

        fun getRetrofit(base_url: String): Retrofit? {
            if (retrofit == null) {
                synchronized(Api::class.java) {
                    if (retrofit == null) {
                        val baseApi = BaseApi()
                        retrofit = baseApi.getRetrofit(base_url)
                    }
                }
            }
            return retrofit
        }
    }

}