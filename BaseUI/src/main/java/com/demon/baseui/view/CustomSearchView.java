package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.demon.baseui.R;
import com.demon.baseui.keyboard.KeyboardUtil;

/**
 * @author DeMon
 * @date 2018/12/18
 * @email 757454343@qq.com
 * @description
 */
public class CustomSearchView extends ConstraintLayout {
    private EditText etQuery;
    private Button btnSearch;
    private OnQueryListener onQueryListener;

    public CustomSearchView(Context context) {
        this(context, null);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.widget_search_view, this);
        etQuery = findViewById(R.id.et_query);
        btnSearch = findViewById(R.id.btn_search);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSearchView);
        String textString = typedArray.getString(R.styleable.CustomSearchView_csv_query);
        etQuery.setHint(textString);
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etQuery.clearFocus();
                KeyboardUtil.hideKeyboard(etQuery);
                String query = etQuery.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    if (onQueryListener != null) {
                        onQueryListener.onQueryTextSubmit(query);
                    }
                }
            }
        });
        typedArray.recycle();
    }

    public void setQueryHint(String query) {
        etQuery.setHint(query);
    }

    public void setOnQueryListener(OnQueryListener onQueryListener) {
        this.onQueryListener = onQueryListener;
    }

    public interface OnQueryListener {
        void onQueryTextSubmit(String query);
    }
}
