package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/6/19
 * @description 带标题的分割线
 */
public class TitleDividingLine extends ConstraintLayout {
    private TextView tvTitle;

    public TitleDividingLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.widget_title_line, this);
        tvTitle = findViewById(R.id.tv_title);
        View line = findViewById(R.id.line);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleDividingLine);
        String title = typedArray.getString(R.styleable.TitleDividingLine_tdl_Title);
        int color = typedArray.getColor(R.styleable.TitleDividingLine_tdl_Color, getResources().getColor(R.color.base_deep_blue));
        float titleSize = typedArray.getDimension(R.styleable.TitleDividingLine_tdl_TitleSize, 18);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        }
        tvTitle.setBackgroundColor(color);
        line.setBackgroundColor(color);
        typedArray.recycle();
    }

}
