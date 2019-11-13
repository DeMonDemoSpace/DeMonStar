package com.demon.baseui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2019/8/2
 * @email 757454343@qq.com
 * @description
 */
public class CustomDialog extends Dialog {

    private View customView = null;
    private LinearLayout customLayout;
    private TextView tvTitle;
    private String titleText;
    private boolean isShowTitle = false;
    private Context mContext;

    public CustomDialog(Context context) {
        super(context, R.style.TransparentDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_custom_dialog);
        customLayout = findViewById(R.id.customLayout);
        tvTitle = findViewById(R.id.tvTitle);
        if (isShowTitle) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(titleText)) {
            tvTitle.setText(titleText);
        }
        if (customView != null) {

            customLayout.addView(customView);
        }
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public CustomDialog setCustomView(View customView) {
        this.customView = customView;
        return this;
    }

    public CustomDialog setTitleText(int id) {
        this.titleText = mContext.getResources().getString(id);
        isShowTitle = true;
        return this;
    }

    public CustomDialog setTitleText(String titleText) {
        this.titleText = titleText;
        isShowTitle = true;
        return this;
    }

    public CustomDialog setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
        return this;
    }
}
