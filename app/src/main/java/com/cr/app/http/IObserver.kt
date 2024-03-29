package com.cr.app.http

import android.app.ProgressDialog
import android.content.Context
import com.cr.app.data.Constants
import com.demon.baseframe.model.BaseObserver
import com.demon.baseui.dialog.ImageProgressDialog
import com.demon.baseutil.ToastUtil
import com.google.gson.Gson


/**
 * @author DeMonnnnnn
 * @date 2019/10/9
 * @email 757454343@qq.com
 * @description
 */
class IObserver : BaseObserver<String> {

    private var listener: OnRequest
    private var mContext: Context
    private var gson: Gson

    constructor(context: Context, listener: OnRequest) : super(context) {
        this.listener = listener
        mContext = context
        gson = Gson()
    }

    constructor(context: Context, listener: OnRequest, isShow: Boolean) : super(context, isShow) {
        this.listener = listener
        mContext = context
        gson = Gson()
    }

    override fun onNextResult(str: String) {
        val t = gson.fromJson<DataBean>(str, DataBean::class.java)
        if (t.getStatus() != Constants.OK) {
            ToastUtil.showErrorToast(mContext, t.getStatus())
            listener.onFail(t.getStatus())
        } else {
            listener.onSucceed(t)
        }
    }

    override fun onErrorResult(e: Throwable) {
        ToastUtil.showErrorToast(mContext, "服务器或网络异常，请稍后再试！")
        listener.onFail("服务器或网络异常，请稍后再试！")
    }


    override fun initDialog(context: Context?): ProgressDialog {
        return ImageProgressDialog(context)
    }
}