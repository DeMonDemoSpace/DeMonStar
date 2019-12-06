package com.cr.app.http

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

}