package com.demon.baselist.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2019/7/18
 * @email 757454343@qq.com
 * @description
 */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public QuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

}
