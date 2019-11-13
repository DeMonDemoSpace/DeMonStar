package com.demon.baselist;

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

/**
 * @author DeMon
 * @date 2019/7/19
 * @email 757454343@qq.com
 * @description
 */
public class DraggableHelper {

    private int from;
    private int deletePos;
    private BaseItemDraggableAdapter mAdapter;
    private ItemTouchHelper itemTouchHelper;

    public DraggableHelper(RecyclerView recyclerView) {
        this.mAdapter = (BaseItemDraggableAdapter) recyclerView.getAdapter();
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public interface DraggableListener {
        void draggableListener(RecyclerView.ViewHolder viewHolder, int from, int to);
    }

    public interface DeleteListener {
        void deleteListener(RecyclerView.ViewHolder viewHolder, int pos);
    }

    public DraggableHelper initDraggable() {
        return initDraggable(null);
    }

    public DraggableHelper initDraggable(final DraggableListener listener) {
        // 开启拖拽
        mAdapter.enableDragItem(itemTouchHelper);
        mAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                from = pos;
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                if (listener != null) {
                    listener.draggableListener(viewHolder, from, pos);
                }
            }
        });
        return this;
    }

    public DraggableHelper initSwipe() {
        return initSwipe(null);
    }

    public DraggableHelper initSwipe(final DeleteListener listener) {
        // 开启滑动删除
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                deletePos = pos;
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                if (listener != null) {
                    listener.deleteListener(viewHolder, deletePos);
                }
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        return this;
    }
}
