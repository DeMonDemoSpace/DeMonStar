package com.demon.baselist.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2019/7/18
 * @email 757454343@qq.com
 * @description
 */
public  abstract class DraggableAdapter<T> extends BaseItemDraggableAdapter<T, BaseViewHolder> {
    public DraggableAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }
}
