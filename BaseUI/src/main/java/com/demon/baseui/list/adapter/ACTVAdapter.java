package com.demon.baseui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.demon.baseui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeMon
 * @date 2018/12/4
 * @email 757454343@qq.com
 * @description
 */
public class ACTVAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<String> list;//该list存放的是最终弹出列表的数据
    private List<String> initList = new ArrayList<>();
    private ArrayFilter mFilter;

    public ACTVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        initList.addAll(list);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 这里必须返回list.get(position)，否则点击条目后输入框显示的是position，而非该position的数据
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TvViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_actv, null);
            holder = new TvViewHolder();
            holder.tv = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (TvViewHolder) convertView.getTag();
        }
        //注意这里不要为convertView添加点击事件，默认是点击后：①下拉窗收起；
        //②点击的条目数据会显示在搜索框中；③光标定位到字符串末位。
        //如果自己添加点击事件，就要首先实现上面的①、②、③。
        holder.tv.setText(list.get(position));
        return convertView;
    }

    class TvViewHolder {
        TextView tv;
    }

    private class ArrayFilter extends Filter {
        /**
         * 在后台线程执行，定义过滤算法
         *
         * @param constraint ：就是你在输入框输入的字符串
         * @return 符合条件的数据结果，会在下面的publishResults方法中将数据传给list
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                //这个newList是实际搜索出的结果集合
                List<String> newList = new ArrayList<>();
                for (String ss : initList) {
                    if (ss.contains(constraint.toString().trim())) {
                        newList.add(ss);
                    }
                }
                //将newList传给results
                results.values = newList;
                results.count = newList.size();
            }
            return results;
        }

        /**
         * 本方法在UI线程执行，用于更新自动完成列表
         *
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {//有符合过滤规则的数据
                list.clear();
                list.addAll((List<String>) results.values);
                notifyDataSetChanged();
            } else {//没有符合过滤规则的数据
                notifyDataSetInvalidated();
            }
        }
    }
}


