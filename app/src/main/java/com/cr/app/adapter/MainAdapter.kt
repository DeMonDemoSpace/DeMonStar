package com.cr.app.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.cr.app.R
import com.cr.app.bean.MainBean
import com.demon.baselist.adapter.SectionAdapter

/**
 * @author liuhui
 * @date 2019/11/29
 * @email 757454343@qq.com
 * @description
 */

class MainAdapter : SectionAdapter<MainBean> {

    constructor(datas: List<MainBean>) : super(R.layout.list_mian_group, R.layout.list_mian_chlid, datas)

    override fun convert(helper: BaseViewHolder, item: MainBean) {
        helper.setText(R.id.tvText, item.content)
    }

    override fun convertHead(helper: BaseViewHolder, item: MainBean) {
        helper.setText(R.id.tvGroup, item.content)
    }
}