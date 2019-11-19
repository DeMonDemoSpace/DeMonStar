package com.demon.baselist;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.demon.baselist.view.PullLoadMoreView;

import java.util.List;

/**
 * Author: DeMon.
 * Date: 2018/3/2.
 * Work:
 */

public class LoadMoreHelper {
    private int dataSize = 15;
    private int pageNum = 0;
    private BaseQuickAdapter adapter;
    private LoadMoreView loadMoreView;
    private RecyclerView recyclerView;

    public LoadMoreHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.adapter = (BaseQuickAdapter) recyclerView.getAdapter();
        this.adapter.bindToRecyclerView(recyclerView);
    }

    public LoadMoreHelper(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.adapter.bindToRecyclerView(recyclerView);
    }

    public LoadMoreView getLoadMoreView() {
        return loadMoreView;
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public interface LoadMoreListener {
        void loadMoreListener(int pageNum);
    }

    /**
     * 设置recyclerView上拉加载更多的view
     */
    public void onLoadMoreListener(final LoadMoreListener listener) {
        //this.adapter.setEnableLoadMore(true);
        if (loadMoreView == null) {
            loadMoreView = new PullLoadMoreView();
        }
        this.adapter.setLoadMoreView(loadMoreView);
        this.adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                if (listener != null) {
                    listener.loadMoreListener(pageNum);
                }
            }
        }, recyclerView);

        this.adapter.disableLoadMoreIfNotFullPage();
    }

    /**
     * 处理列表刷新数据或者加载更多数据完成之后的view变换
     *
     * @param dataList
     * @return 返回分页数
     */
    public void addDataList(List dataList) {
        if (pageNum == 0) {
            setNewDataList(dataList);
        } else {
            this.adapter.addData(dataList);
            if (dataList.size() < dataSize) {
                this.adapter.loadMoreEnd(); //加载完成，无法继续上拉加载更多
            } else {
                this.adapter.loadMoreComplete();
            }
        }
    }

    public void handlerLoadFailed() {
        this.pageNum--;
        //this.adapter.enableLoadMoreEndClick(true);
        this.adapter.loadMoreFail();
    }

    public void setNewDataList(List dataList) {
        this.pageNum = 0;
        this.adapter.setNewData(dataList);
        this.adapter.disableLoadMoreIfNotFullPage();
    }


    public void setUpLoadFalse() {
        this.adapter.setEnableLoadMore(false);
    }


}
