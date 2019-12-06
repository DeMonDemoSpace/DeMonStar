package com.cr.app.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseViewHolder
import com.cr.app.R
import com.cr.app.act.mvp.WeatherActivity
import com.cr.app.bean.MainBean
import com.demon.baseframe.activity.WebViewActivity
import com.demon.baselist.adapter.SectionAdapter

/**
 * @author liuhui
 * @date 2019/11/29
 * @email 757454343@qq.com
 * @description
 */

class MainAdapter : SectionAdapter<MainBean> {

    constructor(datas: List<MainBean>) : super(R.layout.list_mian_chlid, R.layout.list_mian_group, datas)

    override fun convert(helper: BaseViewHolder, item: MainBean) {
        helper.setText(R.id.tvText, item.content)
        helper.itemView.setOnClickListener {
            when (item.content) {
                "MVP" -> {
                    mContext.startActivity(Intent(mContext, WeatherActivity::class.java))
                }
                "WebView" -> {
                    mContext.startActivity(WebViewActivity.newIntent(mContext, "https://www.baidu.com/", true))
                }
            }
        }
    }

    override fun convertHead(helper: BaseViewHolder, item: MainBean) {
        helper.setText(R.id.tvGroup, item.content)
    }
}