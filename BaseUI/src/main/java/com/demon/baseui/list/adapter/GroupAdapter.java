package com.demon.baseui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demon.baseui.R;
import com.demon.baseui.list.holder.DataViewHolder;
import com.demon.baseui.list.holder.GroupViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2017/12/11
 * @description
 */

public abstract class GroupAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<T> list;
    private LayoutInflater mInflater;
    private int childId, groupId;

    private View mEmptyView;
    // 底部控件
    private View mFooterView;
    private boolean isHasEmpty = false;
    private boolean isHasFooter = false;

    // item 的三种类型
    private static final int TYPE_GROUP = 0;//显示分组栏
    private static final int TYPE_CHILD = 1;//显示数据栏
    private static final int TYPE_EMPTY = 2; // 空
    private static final int TYPE_FOOTER = 3; // footer

    public GroupAdapter(Context context, int groupId, int childId, List<T> list) {
        this.context = context;
        this.list = list;
        this.groupId = groupId;
        this.childId = childId;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GROUP) {
            View view = mInflater.inflate(groupId, parent, false);
            return new GroupViewHolder(context, view);
        } else if (viewType == TYPE_CHILD) {
            View view = mInflater.inflate(childId, parent, false);
            return new DataViewHolder(context, view);
        } else if (viewType == TYPE_EMPTY) {
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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupViewHolder) {
            bindItem((DataViewHolder) holder, position, list.get(position));
            bindGroup((GroupViewHolder) holder, position, list.get(position));
        } else if (holder instanceof DataViewHolder && list.size() > 0 && position != list.size()) {
            bindItem((DataViewHolder) holder, position, list.get(position));
        }

        if (isHasFooter && position == list.size() && mFooterView == null && list.size() > 0) {
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.setText(R.id.tv_footer, list.size() + "");
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
        //第一个要显示分组
        if (position == 0) {
            return TYPE_GROUP;
        }

        return getViewType(position);
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0 && isHasEmpty) {
            return 1;
        }
        if (list.size() > 0 && isHasFooter) {
            return list.size() + 1;
        }
        return list.size();
    }

    //判定分组的依据
    public abstract int getViewType(int position);

    //绑定分组的视图
    public abstract void bindGroup(GroupViewHolder holder, int position, T t);

    //绑定组内的视图
    public abstract void bindItem(DataViewHolder holder, int position, T t);

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
