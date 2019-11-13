package com.demon.baseui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.demon.baseui.list.holder.DataViewHolder;

import java.util.List;

/**
 * Author: DeMon.
 * Date: 2018/1/29.
 * Work:
 */

public abstract class PagerDataAdapter<T> extends PagerAdapter {
    private List<T> list;
    private Context context;
    private LayoutInflater mInflater;
    private int layoutId;

    public PagerDataAdapter(Context context, int layoutId, List<T> list) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//及时销毁界面，防止内存溢出
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//必须实现
        View view = null;
        DataViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(layoutId, container, false);
            holder = new DataViewHolder(context, view);
            view.setTag(holder);
        } else {
            holder = (DataViewHolder) view.getTag();
        }
        convert(holder, position, list.get(position));
        container.addView(view);
        return view;
    }

    //抽象方法回调
    public abstract void convert(DataViewHolder holder, int position, T t);
}
