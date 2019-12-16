package com.cr.app.net

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import com.cr.app.data.Constants
import com.demon.baseframe.helper.BroadcastHelper
import com.demon.baseutil.NetWorkUtils

/**
 * @author liuhui
 * @date 2019/12/16
 * @email 757454343@qq.com
 * @description
 */
class NetWorkService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        BroadcastHelper.getInstance(this).addAction(ConnectivityManager.CONNECTIVITY_ACTION, object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Constants.NET = NetWorkUtils.isNetConnected(context)
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        BroadcastHelper.getInstance(this).destroy(ConnectivityManager.CONNECTIVITY_ACTION)
    }
}