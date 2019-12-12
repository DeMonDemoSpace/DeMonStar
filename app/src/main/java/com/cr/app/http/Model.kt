package com.cr.app.http

import com.cr.app.App
import com.demon.baseframe.model.BaseModel
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker


/**
 * @author DeMonnnnnn
 * @date 2019/10/10
 * @email 757454343@qq.com
 * @description
 */
class Model : BaseModel {
    private val WEATHER_URL = "https://free-api.heweather.net/s6/weather/"

    private val CITY_URL = "https://search.heweather.net/"


    private var providers: CacheProviders
    private var weatherService: ApiService
    private var cityService: ApiService

    companion object {
        fun getInstance() = Helper.instance
    }

    private object Helper {
        val instance = Model()
    }

    constructor() {
        weatherService = Api.getRetrofit(WEATHER_URL)!!.create(ApiService::class.java)
        cityService = Api.getRetrofit(CITY_URL)!!.create(ApiService::class.java)
        providers = RxCache.Builder().persistence(App.getApplication().externalCacheDir, GsonSpeaker()).using(CacheProviders::class.java)
    }

    fun getNowWeather(location: String, refresh: Boolean, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        addSubcription(providers.getWeatherNow(weatherService.getNowWeather(location)), observer)
        //addSubcription(weatherService.getNowWeather(location), observer)
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