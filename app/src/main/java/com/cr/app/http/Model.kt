package com.cr.app.http

import com.cr.app.App
import com.cr.app.data.Constants
import com.demon.baseframe.model.BaseModel
import com.demon.baseframe.model.BaseService
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictProvider
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
    private var cityService: BaseService
    private var service: BaseService

    companion object {
        fun getInstance() = Helper.instance
    }

    private object Helper {
        val instance = Model()
    }

    constructor() {
        service = Api.getRetrofit(WEATHER_URL)!!.create(BaseService::class.java)

        weatherService = Api.getRetrofit(WEATHER_URL)!!.create(ApiService::class.java)

        cityService = Api.getRetrofit(CITY_URL)!!.create(BaseService::class.java)
        providers = RxCache.Builder()
            .useExpiredDataIfLoaderNotAvailable(true)
            .persistence(App.getApplication().cacheDir, GsonSpeaker())
            .using(CacheProviders::class.java)
    }

    fun getNowWeather(location: String, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        //addSubcription(weatherService.getNowWeather(location), observer)
        addSubcription(providers.getWeather(weatherService.getNowWeather(location), DynamicKey(location), EvictProvider(Constants.NET)), observer)
    }

    fun get(url: String, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        addSubcription(service.get(url), observer)
    }


    fun get(url: String, map: Map<String, String>, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        addSubcription(service.get(url, map), observer)
    }


    fun getCity(url: String, map: Map<String, String>, listener: OnRequest) {
        val observer = IObserver(mContext, listener)
        //addSubcription(cityService.get(url, map), observer)

        addSubcription(providers.getCity(cityService.get(url, map), DynamicKey(map), EvictProvider(Constants.NET)), observer)

    }


}