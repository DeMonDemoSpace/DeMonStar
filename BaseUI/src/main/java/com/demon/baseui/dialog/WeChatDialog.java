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
 * @date 2019/7/19
 * @email 757454343@qq.com
 * @description 仿微信Dialog
 */
public class WeChatDialog extends Dialog {
    private Context mContext;
    private WeChatDialog dialog;
    private int textGravity;

    public WeChatDialog(Context context) {
        this(context, Gravity.CENTER);
    }

    public WeChatDialog(Context context, int textGravity) {
        super(context, R.style.TransparentDialog);
        mContext = context;
        dialog = this;
        this.textGravity = textGravity;
    }

    private TextView tvTitle, tvContent, tvLeft, tvRight, tvSingle;
    private View customView = null;
    private LinearLayout doubleLayout, singleLayout, customLayout;

    private CharSequence contentStr;
    private String titleText, contentText, leftText, rightText, singleText;
    private boolean isSingle = false, isShowTitle = false;
    private int titleColor = 0;
    private int contentColor = 0;
    private int leftColor = 0;
    private int rightColor = 0;
    private int singColor = 0;
    private SingleListener singleListener;
    private DoubleListener doubleListener;

    public interface SingleListener {
        void onSingleClick(Dialog dialog);
    }

    public interface DoubleListener {
        void onLeftClick(Dialog dialog);

        void onRightClick(Dialog dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_wechat_dialog);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContext);
        tvContent.setGravity(textGravity);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);
        tvSingle = findViewById(R.id.tvSingle);
        doubleLayout = findViewById(R.id.doubleLayout);
        singleLayout = findViewById(R.id.singleLayout);
        customLayout = findViewById(R.id.customLayout);
        if (isShowTitle) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(titleText)) {
            tvTitle.setText(titleText);
        }

        if (!TextUtils.isEmpty(contentText)) {
            tvContent.setText(contentText);
        }

        if (!TextUtils.isEmpty(contentStr)) {
            tvContent.setText(contentStr);
        }

        if (!TextUtils.isEmpty(leftText)) {
            tvLeft.setText(leftText);
        }

        if (!TextUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
        }

        if (!TextUtils.isEmpty(singleText)) {
            tvSingle.setText(singleText);
        }

        if (isSingle) {
            singleLayout.setVisibility(View.VISIBLE);
            doubleLayout.setVisibility(View.GONE);
        } else {
            singleLayout.setVisibility(View.GONE);
            doubleLayout.setVisibility(View.VISIBLE);
        }

        tvSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleListener != null) {
                    singleListener.onSingleClick(dialog);
                }
                dismiss();
            }
        });

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doubleListener != null) {
                    doubleListener.onLeftClick(dialog);
                }
                dismiss();
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doubleListener != null) {
                    doubleListener.onRightClick(dialog);
                }
                dismiss();
            }
        });

        if (titleColor != 0) {
            tvTitle.setTextColor(mContext.getResources().getColor(titleColor));
        }
        if (contentColor != 0) {
            tvContent.setTextColor(mContext.getResources().getColor(contentColor));
        }
        if (leftColor != 0) {
            tvLeft.setTextColor(mContext.getResources().getColor(leftColor));
        }

        if (rightColor != 0) {
            tvRight.setTextColor(mContext.getResources().getColor(rightColor));
        }

        if (singColor != 0) {
            tvSingle.setTextColor(mContext.getResources().getColor(singColor));
        }

        if (customView != null) {
            tvContent.setVisibility(View.GONE);
            customLayout.setVisibility(View.VISIBLE);
            customLayout.addView(customView);
        }

        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public WeChatDialog setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
        return this;
    }

    public WeChatDialog setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }


    public WeChatDialog setContentColor(int contentColor) {
        this.contentColor = contentColor;
        return this;
    }

    public WeChatDialog setSingle(boolean single) {
        isSingle = single;
        return this;

    }

    public WeChatDialog setTitleText(int id) {
        this.titleText = mContext.getResources().getString(id);
        isShowTitle = true;
        return this;
    }

    public WeChatDialog setTitleText(String titleText) {
        this.titleText = titleText;
        isShowTitle = true;
        return this;
    }

    public WeChatDialog setContentText(int id) {
        this.contentText = mContext.getResources().getString(id);
        return this;
    }

    public WeChatDialog setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public WeChatDialog setLeftText(int id) {
        this.leftText = mContext.getResources().getString(id);
        return this;
    }

    public WeChatDialog setLeftText(String leftText) {
        this.leftText = leftText;
        return this;
    }

    public WeChatDialog setRightText(int id) {
        this.rightText = mContext.getResources().getString(id);
        return this;
    }

    public WeChatDialog setRightText(String rightText) {
        this.rightText = rightText;
        return this;
    }

    public WeChatDialog setSingleText(String singleText) {
        this.singleText = singleText;
        return this;
    }

    public WeChatDialog setSingleListener(SingleListener singleListener) {
        this.singleListener = singleListener;
        return this;
    }

    public WeChatDialog setDoubleListener(DoubleListener doubleListener) {
        this.doubleListener = doubleListener;
        return this;
    }


    public WeChatDialog setContentStr(CharSequence contentStr) {
        this.contentStr = contentStr;
        return this;
    }

    public WeChatDialog setLeftColor(int leftColor) {
        this.leftColor = leftColor;
        return this;
    }

    public WeChatDialog setRightColor(int rightColor) {
        this.rightColor = rightColor;
        return this;
    }

    public WeChatDialog setSingColor(int singColor) {
        this.singColor = singColor;
        return this;
    }

    public WeChatDialog setCustomView(View customView) {
        this.customView = customView;
        return this;
    }

    public WeChatDialog setTextGravity(int textGravity) {
        this.textGravity = textGravity;
        return this;
    }
}
