package com.cr.app.http

import com.cr.app.data.Constants
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author liuhui
 * @date 2019/12/3
 * @email 757454343@qq.com
 * @description
 */
interface ApiService {


    @GET
    fun get(@Url url: String): Observable<DataBean>

    @GET
    fun get(@Url url: String, @QueryMap map: Map<String, String>): Observable<DataBean>


    @POST
    fun post(@Url url: String, @Body json: JsonObject): Observable<DataBean>

    //@Headers("Cache-Control:public ,max-age=60")
    @GET("now")
    fun getNowWeather(@Query(Constants.LOCATION) location: String): Observable<String>

}