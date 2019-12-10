package com.cr.app.bean
import com.google.gson.annotations.SerializedName


/**
 * @author liuhui
 * @date 2019/12/10
 * @email 757454343@qq.com
 * @description
 */
data class City(
    @SerializedName("admin_area")
    var adminArea: String = "",
    @SerializedName("cid")
    var cid: String = "",
    @SerializedName("cnty")
    var cnty: String = "",
    @SerializedName("lat")
    var lat: String = "",
    @SerializedName("location")
    var location: String = "",
    @SerializedName("lon")
    var lon: String = "",
    @SerializedName("parent_city")
    var parentCity: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("tz")
    var tz: String = ""
)