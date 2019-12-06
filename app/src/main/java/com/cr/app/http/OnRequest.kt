package com.cr.app.http

/**
 * @author DeMonnnnnn
 * @date 2019/10/12
 * @email 757454343@qq.com
 * @description 请求结果的回调
 */
interface OnRequest {
    fun onSucceed(obj: DataBean)//请求成功，200

    fun onFail(msg: String) //请求失败，非200的情况

}