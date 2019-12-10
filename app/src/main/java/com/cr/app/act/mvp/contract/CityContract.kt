package com.cr.app.act.mvp.contract

import com.cr.app.bean.City
import com.cr.app.data.Constants
import com.cr.app.http.DataBean
import com.cr.app.http.Model
import com.cr.app.http.OnRequest
import com.demon.baseframe.model.BasePresenter
import com.demon.baseframe.model.BaseView
import com.google.gson.reflect.TypeToken

/**
 * @author liuhui
 * @date 2019/12/10
 * @email 757454343@qq.com
 * @description
 */
class CityContract {
    interface View : BaseView {
        fun citys(cityList: List<City>)
    }


    class Presenter : BasePresenter<View>() {

        fun search(city: String) {

            val map = HashMap<String, String>()
            map[Constants.LOCATION] = city
            Model.getInstance().getCity("find", map, object : OnRequest {
                override fun onSucceed(obj: DataBean) {
                    val list = obj.getDataList<List<City>>("basic", object : TypeToken<List<City>>() {}.type)
                    mView.citys(list)
                }

                override fun onFail(msg: String) {
                    mView.citys(arrayListOf())
                }
            })
        }


        fun topCity() {
            val map = HashMap<String, String>()
            map["group"] = "world"
            map["number"] = "30"
            Model.getInstance().getCity("top", map, object : OnRequest {
                override fun onSucceed(obj: DataBean) {
                    val list = obj.getDataList<List<City>>("basic", object : TypeToken<List<City>>() {}.type)
                    mView.citys(list)
                }

                override fun onFail(msg: String) {
                    mView.citys(arrayListOf())
                }
            })
        }
    }
}