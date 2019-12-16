package com.cr.app.act.mvp.contract

import com.cr.app.bean.WeatherNow
import com.cr.app.data.Constants
import com.cr.app.http.DataBean
import com.cr.app.http.Model
import com.cr.app.http.OnRequest
import com.demon.baseframe.model.BasePresenter
import com.demon.baseframe.model.BaseView

/**
 * @author liuhui
 * @date 2019/12/5
 * @email 757454343@qq.com
 * @description
 */
class WeatherContract {

    interface View : BaseView {
        fun weather(now: WeatherNow)
    }

    class Presenter : BasePresenter<View>() {
        fun getNormalWeather(city: String) {
            val map = HashMap<String, String>()
            map[Constants.LOCATION] = city
            Model.getInstance().getNowWeather(city, object : OnRequest {
                override fun onSucceed(obj: DataBean) {
                    mView.weather(obj.getDataBean("now", WeatherNow::class.java))
                }

                override fun onFail(msg: String) {
                }
            })
        }
    }
}