package com.cr.app.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @author liuhui
 * @date 2019/11/29
 * @email 757454343@qq.com
 * @description
 */
class MainBean : SectionEntity<String> {

    var content: String = ""

    constructor(content: String) : super(false,"") {
        this.content = content
    }

    constructor(isHeader: Boolean, content: String) : super(isHeader, content)


}