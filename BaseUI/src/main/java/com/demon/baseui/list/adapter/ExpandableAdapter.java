package com.demon.baseui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.demon.baseui.list.holder.DataViewHolder;
import com.demon.baseui.list.holder.GroupViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2018/7/6
 * @description
 */
public abstract class ExpandableAdapter<T> extends BaseExpandableListAdapter {
    private Context context;
    private int childLayoutId, groupLayoutId;
    private List<T> list;

    public ExpandableAdapter(Context context, int groupLayoutId, int childLayoutId, List<T> list) {
        this.context = context;
        this.childLayoutId = childLayoutId;
        this.groupLayoutId = groupLayoutId;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getChildrenCounts(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getChildObject(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(groupLayoutId, null);
            holder = new GroupViewHolder(context, convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        bindGroup(holder, groupPosition, isExpanded, list.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        DataViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(childLayoutId, null);
            holder = new DataViewHolder(context, convertView);
            convertView.setTag(holder);
        } else {
            holder = (DataViewHolder) convertView.getTag();
        }
        bindItem(holder, groupPosition, childPosition, isLastChild, list.get(groupPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public abstract int getChildrenCounts(int groupPosition);

    public abstract Object getChildObject(int groupPosition, int childPosition);

    public abstract void bindGroup(GroupViewHolder holder, int groupPosition, boolean isExpanded, T t);

    public abstract void bindItem(DataViewHolder holder, int groupPosition, int childPosition, boolean isLastChild, T t);

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }
}
