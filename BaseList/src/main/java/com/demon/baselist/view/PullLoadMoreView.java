package com.demon.baselist.view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.demon.baselist.R;

/**
 * @author DeMon
 * @date 2019/7/18
 * @email 757454343@qq.com
 * @description
 */
public class PullLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.widget_load_more;
    }


    @Override
    protected int getLoadingViewId() {
        return R.id.load_more;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_end;
    }
}
