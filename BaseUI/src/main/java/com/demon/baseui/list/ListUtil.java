package com.demon.baseui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demon.baseui.R;

/**
 * Author: DeMon.
 * Date: 2018/3/2.
 * Work:
 */

public class ListUtil {
    /**
     * RecyclerView 移动到当前位置
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

    public static void setEmptyListView(ListView listview) {
        View view = LayoutInflater.from(listview.getContext()).inflate(R.layout.view_default_empty, null);
        setEmptyListView(listview, view);
    }

    /**
     * 设置ListView的EmptyView
     * setEmptyView
     *
     * @param listview
     * @param emptyView <p>网上对Api解释的非常清楚，将EmptyView添加到最外层的ViewGroup上。</p>
     */
    public static void setEmptyListView(ListView listview, View emptyView) {
        FrameLayout emptyLayout;
        if (listview.getEmptyView() == null) {
            emptyLayout = new FrameLayout(listview.getContext());
            emptyLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            emptyLayout.addView(emptyView);
            emptyView.setVisibility(View.VISIBLE);
            getParentView((ViewGroup) listview.getParent()).addView(emptyLayout);
            listview.setEmptyView(emptyLayout);
        } else {
            emptyLayout = (FrameLayout) listview.getEmptyView();
            emptyLayout.removeAllViews();
            emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.addView(emptyView);
        }
    }

    private static ViewGroup getParentView(ViewGroup parent) {
        ViewGroup tempVg = parent;
        if (parent.getParent() != null && parent.getParent() instanceof ViewGroup) {
            tempVg = (ViewGroup) parent.getParent();
            getParentView(tempVg);
        } else {
            return tempVg;
        }
        return tempVg;
    }



}
