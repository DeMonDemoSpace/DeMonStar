package com.cr.app.http

import com.cr.app.data.Constants
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.lang.reflect.Type


/**
 * @author liuhui
 * @date 2019/12/3
 * @email 757454343@qq.com
 * @description
 */
class DataBean {
    private var gson: Gson = Gson()
    @SerializedName("HeWeather6")
    var heWeather6: List<Any> = listOf()

    fun getData(): String {
        return gson.toJson(heWeather6[0])

    }

    fun getKeyData(key: String): String {
        var jsonObject = JSONObject(gson.toJson(heWeather6[0]))
        return jsonObject.get(key).toString()
    }


    fun <T> getDataBean(key: String, cls: Class<T>): T {
        return gson.fromJson<T>(JSONObject(gson.toJson(heWeather6[0])).getJSONObject(key).toString(), cls)
    }

    fun <T> getDataList(key: String, type: Type): T {

        return gson.fromJson(JSONObject(gson.toJson(heWeather6[0])).getJSONArray(key).toString(), type)
    }


    fun getStatus(): String {
        var jsonObject = JSONObject(gson.toJson(heWeather6[0]))
        if (jsonObject.has(Constants.STATUS)) {
            return jsonObject.getString(Constants.STATUS)
        }
        return ""
    }
}

