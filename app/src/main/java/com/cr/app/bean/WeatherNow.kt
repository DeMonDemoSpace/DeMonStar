package com.cr.app.bean
import com.google.gson.annotations.SerializedName


/**
 * @author liuhui
 * @date 2019/12/5
 * @email 757454343@qq.com
 * @description
 */
data class WeatherNow(
    @SerializedName("cloud")
    var cloud: String = "",
    @SerializedName("cond_code")
    var condCode: String = "",
    @SerializedName("cond_txt")
    var condTxt: String = "",
    @SerializedName("fl")
    var fl: String = "",
    @SerializedName("hum")
    var hum: String = "",
    @SerializedName("pcpn")
    var pcpn: String = "",
    @SerializedName("pres")
    var pres: String = "",
    @SerializedName("tmp")
    var tmp: String = "",
    @SerializedName("vis")
    var vis: String = "",
    @SerializedName("wind_deg")
    var windDeg: String = "",
    @SerializedName("wind_dir")
    var windDir: String = "",
    @SerializedName("wind_sc")
    var windSc: String = "",
    @SerializedName("wind_spd")
    var windSpd: String = ""
)