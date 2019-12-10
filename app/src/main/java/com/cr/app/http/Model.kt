package com.cr.app.http

import com.demon.baseframe.model.BaseModel


/**
 * @author DeMonnnnnn
 * @date 2019/10/10
 * @email 757454343@qq.com
 * @description
 */
class Model : BaseModel {
    private val WEATHER_URL = "https://free-api.heweather.net/s6/weather/"

    private val CITY_URL = "https://search.heweather.net/"

    companion object {
        lateinit var weatherService: ApiService
        lateinit var cityService: ApiService

        fun getInstance() = Helper.instance
    }

    private object Helper {
        val instance = Model()
    }

    constructor() {
        weatherService = Api.getRetrofit(WEATHER_URL)!!.create(ApiService::class.java)
        cityService = Api.getRetrofit(CITY_URL)!!.create(ApiService::class.java)
    }


    fun get(url: String, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        addSubcription(weatherService.get(url), observer)
    }

    fun get(url: String, map: Map<String, String>, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        addSubcription(weatherService.get(url, map), observer)
    }


    fun getCity(url: String, map: Map<String, String>, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        addSubcription(cityService.get(url, map), observer)
    }


}