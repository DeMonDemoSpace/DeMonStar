package com.demon.baseui.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * Created by DeMon on 2017/7/19.
 * 继承实现RecyclerView.OnScrollListener滑动监听
 */

public abstract class OnRecyclerScrollBottomListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private int currentPage = 0;

    private boolean loading = true;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int num; //一次加载的数据量,最小要大于屏幕可显示数量
    private RecyclerView.LayoutManager manager;

    public OnRecyclerScrollBottomListener() {
        previousTotal = 0;
        currentPage = 0;
        num = 30;
    }


    public OnRecyclerScrollBottomListener(int num) {
        previousTotal = 0;
        currentPage = 0;
        this.num = num;
    }

    /**
     * 重写监听滑动状态改变的方法
     * 滑动状态改变后才刷新数据
     *
     * @param recyclerView
     * @param newState
     */
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        manager = recyclerView.getLayoutManager();
        visibleItemCount = recyclerView.getChildCount();//可见数据量
        totalItemCount = manager.getItemCount();//总数据量
        if (manager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
        } else if (manager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) manager).findFirstVisibleItemPosition();
        } else {
            firstVisibleItem = ((StaggeredGridLayoutManager) manager).findFirstVisibleItemPositions(null)[0];
        }
        //重置初始状态，消除多个列表滑动加载的冲突，一个列表加载不同类别的数据的冲突
        if (firstVisibleItem == 0 && totalItemCount <= num) {
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

    /**
     * 为防止一直滑动连续不断的加载
     * 所以不重写此方法
     */
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }

    public abstract void onLoadMore(int currentPage);

}
