package com.cr.app.http

import android.annotation.SuppressLint
import com.cr.app.App
import com.demon.baseframe.model.BaseModel
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker


/**
 * @author DeMonnnnnn
 * @date 2019/10/10
 * @email 757454343@qq.com
 * @descrip
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
        providers = RxCache.Builder()
            .useExpiredDataIfLoaderNotAvailable(true)
            .persistence(App.getApplication().cacheDir, GsonSpeaker())
            .using(CacheProviders::class.java)
    }

    @SuppressLint("CheckResult")
    fun getNowWeather(location: String, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        //addSubcription(providers.getWeatherNow(weatherService.getNowWeather(location), DynamicKey(location), EvictProvider(Constants.NET)),)
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