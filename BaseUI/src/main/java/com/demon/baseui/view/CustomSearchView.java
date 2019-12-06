package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.demon.baseui.R;
import com.demon.baseui.keyboard.KeyboardUtil;
import com.demon.baseutil.ToastUtil;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author DeMon
 * @date 2018/12/18
 * @email 757454343@qq.com
 * @description
 */
public class CustomSearchView extends ConstraintLayout {
    private EditText etQuery;
    private TextView btnSearch;
    private OnQueryListener onQueryListener;
    private Context mContext;

    public CustomSearchView(Context context) {
        this(context, null);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.widget_search_view, this);
        etQuery = findViewById(R.id.et_query);
        btnSearch = findViewById(R.id.btn_search);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSearchView);
        String textString = typedArray.getString(R.styleable.CustomSearchView_csv_query);
        etQuery.setHint(textString);
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(etQuery);
                if (onQueryListener != null) {
                    onQueryListener.onCancel();
                }

            }
        });
        etQuery.setInputType(typedArray.getInteger(R.styleable.CustomSearchView_csv_inputType, EditorInfo.TYPE_CLASS_TEXT));
        etQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    KeyboardUtil.hideKeyboard(etQuery);
                    String query = etQuery.getText().toString().trim();
                    if (!TextUtils.isEmpty(query)) {
                        if (onQueryListener != null) {
                            onQueryListener.onQueryTextSubmit(query);
                        }
                    } else {
                        ToastUtil.showWarningToast(mContext, "搜索内容不能为空！");
                    }
                }
                return false;

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

        void onCancel();
    }
}
