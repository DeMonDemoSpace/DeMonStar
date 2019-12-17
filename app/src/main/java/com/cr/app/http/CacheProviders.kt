package com.cr.app.http

import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictProvider
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit


/**
 * @author liuhui
 * @date 2019/12/12
 * @email 757454343@qq.com
 * @description
 */
interface CacheProviders {

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES) //缓存有效期24小时
    fun getWeather(observable: Observable<String>, key: DynamicKey, evictProvider: EvictProvider): Observable<String>


    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES) //缓存有效期24小时
    fun getCity(observable: Observable<String>, key: DynamicKey, evictProvider: EvictProvider): Observable<String>
}