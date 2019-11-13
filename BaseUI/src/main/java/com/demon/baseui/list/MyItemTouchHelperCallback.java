package com.demon.baseui.list;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author DeMon
 * @date 2017/11/24
 * @description RecyclverView的手势滑动回调
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private MyItemTouchCallBackListener mMyItemTouchCallBackListener;

    public MyItemTouchHelperCallback(MyItemTouchCallBackListener myItemTouchCallBackListener) {
        mMyItemTouchCallBackListener = myItemTouchCallBackListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags = 0;
        int swipeFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是Grid布局，则不能滑动，只能上下左右拖动
            dragFlags =
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else if (layoutManager instanceof LinearLayoutManager) {
            // 如果是纵向Linear布局，则能上下拖动，左右滑动
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                // 如果是横向Linear布局，则能左右拖动，上下滑动
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
        }
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags); //该方法指定可进行的操作
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return mMyItemTouchCallBackListener.onMove(recyclerView, viewHolder, target);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mMyItemTouchCallBackListener.onSwiped(viewHolder, direction);
    }

    public interface MyItemTouchCallBackListener {
        boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                       RecyclerView.ViewHolder target);

        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }
}
