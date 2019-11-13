package com.demon.baselist.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.demon.baselist.R;

/**
 * @author DeMon
 * @date 2019/7/18
 * @email 757454343@qq.com
 * @description
 */
public class PullRefreshLayout extends SwipeRefreshLayout {
    public PullRefreshLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorPrimary);
    }

    public void refreshComplete() {
        if (this.isRefreshing()) {
            this.setRefreshing(false);
        }
    }
}
