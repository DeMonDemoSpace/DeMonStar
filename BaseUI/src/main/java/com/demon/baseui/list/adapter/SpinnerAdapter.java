package com.demon.baseui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.demon.baseui.list.holder.DataViewHolder;

import java.util.List;

/**
 * @author DeMon
 * @date 2018/10/15
 * @description Spinner适配器
 */
public abstract class SpinnerAdapter<T> extends BaseAdapter {

    private List<T> mList;
    private Context mContext;
    private int layoutId;

    public SpinnerAdapter(Context pContext, int layoutId, List<T> pList) {
        this.mContext = pContext;
        this.mList = pList;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, null);
            holder = new DataViewHolder(mContext, convertView);
            convertView.setTag(holder);
        } else {
            holder = (DataViewHolder) convertView.getTag();
        }
        convert(holder, position, mList.get(position));
        return convertView;
    }

    //抽象方法回调
    public abstract void convert(DataViewHolder holder, int position, T t);
}
