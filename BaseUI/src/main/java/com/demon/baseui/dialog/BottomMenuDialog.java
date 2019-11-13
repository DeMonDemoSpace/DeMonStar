package com.demon.baseui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demon.baseui.R;
import com.demon.baseui.list.adapter.DataAdapter;
import com.demon.baseui.list.holder.DataViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeMon
 * @date 2019/7/19
 * @email 757454343@qq.com
 * @description
 */
public class BottomMenuDialog extends Dialog {
    private Context mContext;

    private List<String> list = new ArrayList<>();
    private List<Integer> colorList = new ArrayList<>();
    private BottomMenuListener menuListener;

    public BottomMenuDialog(Context context) {
        super(context, R.style.TransparentDialog);
        mContext = context;
    }

    public interface BottomMenuListener {
        void menuListener(String text, int pos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_bottom_menu);
        setCanceledOnTouchOutside(false);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        RecyclerView rvMenu = findViewById(R.id.rvMenu);
        rvMenu.setLayoutManager(new LinearLayoutManager(mContext));

        DataAdapter<String> adapter = new DataAdapter<String>(mContext, R.layout.list_bottom_menu, list) {
            @Override
            public void convert(DataViewHolder holder, final int position, final String s) {
                holder.setText(R.id.tvMenu, s);
                if (colorList != null && colorList.size() == list.size()) {
                    if (colorList.get(position) != 0) {
                        holder.setTextColorRes(R.id.tvMenu, colorList.get(position));
                    } else {
                        holder.setTextColorRes(R.id.tvMenu, R.color.base_text);
                    }
                } else {
                    holder.setTextColorRes(R.id.tvMenu, R.color.base_text);
                }
                View view = holder.getView(R.id.line);
                if (position == list.size() - 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (menuListener != null) {
                            menuListener.menuListener(s, position);
                        }
                        dismiss();
                    }
                });
            }
        };
        rvMenu.setAdapter(adapter);
    }

    public BottomMenuDialog setMenuList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        return this;
    }

    public BottomMenuDialog setMenuColors(List<Integer> list) {
        this.colorList.clear();
        this.colorList.addAll(list);
        return this;
    }

    public BottomMenuDialog setMenuListener(BottomMenuListener menuListener) {
        this.menuListener = menuListener;
        return this;
    }
}
