package com.demon.baseui.list;

import android.widget.AbsListView;
import android.widget.ExpandableListView;

/**
 * @author DeMon
 * @date 2018/11/6
 * @description
 */
public abstract class OnListViewScrollBottomListener implements AbsListView.OnScrollListener {
    private static final String TAG = "OnListViewScrollBottom";
    private int previousTotal = 0;
    private int currentPage = 0;
    private boolean loading = true;
    private int num; //一次加载的数据量,最小要大于屏幕可显示数量

    public OnListViewScrollBottomListener() {
        previousTotal = 0;
        currentPage = 0;
        num = 30;
    }

    public OnListViewScrollBottomListener(int num) {
        previousTotal = 0;
        currentPage = 0;
        this.num = num;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    /**
     * @param absListView      列表
     * @param firstVisibleItem 第一个可见的位置
     * @param visibleItemCount 屏幕中可见数量
     * @param totalItemCount   列表中item总数
     */
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int childCount = totalItemCount;
        //当列表为ExpandableListView时，totalItemCount = GroupCount+childCount
        if (absListView instanceof ExpandableListView) {
            ExpandableListView expandableListView = (ExpandableListView) absListView;
            childCount = totalItemCount - expandableListView.getExpandableListAdapter().getGroupCount();
        }
        //重置初始状态，消除多个列表滑动加载的冲突，一个列表加载不同类别的数据的冲突
        if (firstVisibleItem == 0 && childCount <= num) {
            previousTotal = 0;
            currentPage = 0;
        }
        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }
        if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem && totalItemCount > visibleItemCount) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);

}

