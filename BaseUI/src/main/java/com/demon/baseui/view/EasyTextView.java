package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.demon.baseui.R;

/**
 * @author DeMonnnnnn
 * @date 2019/10/15
 * @email 757454343@qq.com
 * @description
 */
public class EasyTextView extends ConstraintLayout {


    protected TextView tvTitle;
    protected EditText tvText;
    protected TextView lineView;
    protected ImageView ivRight;

    public EasyTextView(Context context) {
        this(context, null);
    }

    public EasyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_easy_input_view, this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyTextView);
        tvTitle = findViewById(R.id.tvTitle);
        tvText = findViewById(R.id.tvText);
        ivRight = findViewById(R.id.ivRight);
        tvTitle.setText(typedArray.getString(R.styleable.EasyTextView_etv_title));
        tvText.setText(typedArray.getString(R.styleable.EasyTextView_etv_text));
        tvText.setHint(typedArray.getString(R.styleable.EasyTextView_etv_hint));
        setTextColor(typedArray.getColor(R.styleable.EasyTextView_etv_textColor, context.getResources().getColor(R.color.base_text)));
        setTitleColor(typedArray.getColor(R.styleable.EasyTextView_etv_titleColor, context.getResources().getColor(R.color.base_title_color)));
        setTextSize((int) typedArray.getDimension(R.styleable.EasyTextView_etv_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14,
                getResources().getDisplayMetrics())));
        setTitleSize((int) typedArray.getDimension(R.styleable.EasyTextView_etv_titleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                getResources().getDisplayMetrics())));
        setTitleIcon(typedArray.getResourceId(R.styleable.EasyTextView_etv_titleIcon, 0));
        setRightImg(typedArray.getResourceId(R.styleable.EasyTextView_etv_rightImg, R.drawable.base_to_right));
        lineView = findViewById(R.id.lineView);
        isShowLine(typedArray.getInt(R.styleable.EasyTextView_etv_line, 0));
        boolean isRight = typedArray.getBoolean(R.styleable.EasyTextView_etv_isRight, true);
        isShowRight(isRight);
        isCanEidt(typedArray.getBoolean(R.styleable.EasyTextView_etv_isCanEdit, true));
        isMust(typedArray.getBoolean(R.styleable.EasyTextView_etv_isMust, false));
        typedArray.recycle();
    }

    public void isMust(boolean isMust) {
        if (isMust) {
            SpannableString spannable = new SpannableString(" *");
            spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.base_red)), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvTitle.append(spannable);
        }
    }

    public void setTextColor(int id) {
        tvText.setTextColor(id);
    }

    public void setTitleColor(int id) {
        tvTitle.setTextColor(id);
    }


    public void setTextSize(int id) {
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, id);
    }

    public void setTitleSize(int id) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, id);
    }

    public void setTitle(String text) {
        tvTitle.setText(text);
    }

    public String getTitle() {
        return tvTitle.getText().toString().trim();
    }

    public void setText(String text) {
        tvText.setText(text);
    }

    public String getText() {
        return tvText.getText().toString().trim();
    }

    public void isShowRight(Boolean isRight) {
        ivRight.setVisibility(isRight ? View.VISIBLE : View.INVISIBLE);
    }


    public void setTitleIcon(int id) {
        if (id == 0) return;
        Drawable drawableLeft = getResources().getDrawable(id);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        tvTitle.setCompoundDrawables(drawableLeft, null, null, null);
    }

    public void isShowLine(int isLine) {
        switch (isLine) {
            case 0:
                lineView.setVisibility(View.VISIBLE);
                lineView.setBackgroundResource(R.drawable.line_solid);
                break;
            case 1:
                lineView.setVisibility(View.VISIBLE);
                lineView.setBackgroundResource(R.drawable.line_dashed);
                break;
            case 2:
                lineView.setVisibility(View.GONE);
                break;
        }
    }

    public void setRightImg(int id) {
        ivRight.setImageResource(id);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public EditText getEditText() {
        return tvText;
    }

    public TextView getLineView() {
        return lineView;
    }

    public ImageView getIvRight() {
        return ivRight;
    }


    public void isCanEidt(boolean flag) {
        tvText.setEnabled(flag);
    }

}
