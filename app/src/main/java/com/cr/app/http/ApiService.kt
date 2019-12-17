package com.cr.app.http

import com.cr.app.data.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author liuhui
 * @date 2019/12/3
 * @email 757454343@qq.com
 * @description
 */
interface ApiService {

    @GET("now")
    fun getNowWeather(@Query(Constants.LOCATION) location: String): Observable<String>

}