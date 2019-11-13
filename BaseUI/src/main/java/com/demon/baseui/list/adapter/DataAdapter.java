package com.demon.baseui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demon.baseui.R;
import com.demon.baseui.list.holder.DataViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2017/11/10
 * @description
 */

public abstract class DataAdapter<T> extends RecyclerView.Adapter<DataViewHolder> {
    private Context context;
    private List<T> list;
    private LayoutInflater mInflater;
    private int layoutId;

    private View mEmptyView;
    // 底部控件
    private View mFooterView;
    private boolean isHasEmpty = false;
    private boolean isHasFooter = false;
    // item 的三种类型
    private static final int TYPE_NORMAL = 1; // 正常的item类型
    private static final int TYPE_EMPTY = 2; // 空
    private static final int TYPE_FOOTER = 3; // footer

    public DataAdapter(Context context, int layoutId, List<T> list) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            if (mEmptyView == null) { //默认空视图
                return new DataViewHolder(context, mInflater.inflate(R.layout.view_default_empty, parent, false));
            } else {
                return new DataViewHolder(context, mEmptyView);
            }
        } else if (viewType == TYPE_FOOTER) {
            if (mFooterView == null) {//默认footer
                return new DataViewHolder(context, mInflater.inflate(R.layout.view_default_footer, parent, false));
            } else {
                return new DataViewHolder(context, mFooterView);
            }
        } else {
            return new DataViewHolder(context, mInflater.inflate(layoutId, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0 && isHasEmpty) {
            return TYPE_EMPTY;
        }

        if (position == list.size() && isHasFooter) {
            //有底部，底部索引为size
            return TYPE_FOOTER;
        }

        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        if (list.size() > 0 && position != list.size()) {
            convert(holder, position, list.get(position));
        }

        if (isHasFooter && position == list.size() && mFooterView == null && list.size() > 0) {
            holder.setText(R.id.tv_footer, list.size() + "");
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0 && isHasEmpty) {
            return 1;
        }
        if (isHasFooter) {
            return list.size() + 1;
        }

        return list.size();
    }

    //抽象方法回调
    public abstract void convert(DataViewHolder holder, int position, T t);

    public Context getContext() {
        return context;
    }

    /**
     * 添加空视图
     *
     * @param empty
     */
    public void setEmptyView(View empty) {
        this.mEmptyView = empty;
        isHasEmpty = true;
        notifyDataSetChanged();
    }

    /**
     * 添加底部视图
     *
     * @param footer
     */
    public void setFooterView(View footer) {
        this.mFooterView = footer;
        isHasFooter = true;
        notifyDataSetChanged();
    }

}
