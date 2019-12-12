package com.cr.app.http

import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictProvider
import io.rx_cache2.LifeCache
import io.rx_cache2.ProviderKey
import java.util.concurrent.TimeUnit


/**
 * @author liuhui
 * @date 2019/12/12
 * @email 757454343@qq.com
 * @description
 */
interface CacheProviders {


    @ProviderKey("WeatherNow")
    @LifeCache(duration = 60, timeUnit = TimeUnit.MINUTES) //缓存有效期24小时
    fun getWeatherNow(observable: Observable<DataBean>): Observable<DataBean>

    @ProviderKey("getWeatherNow")
    @LifeCache(duration = 60, timeUnit = TimeUnit.MINUTES) //缓存有效期24小时
    fun getWeatherNow(observable: Observable<DataBean>, key: DynamicKey, evictProvider: EvictProvider): Observable<DataBean>
}