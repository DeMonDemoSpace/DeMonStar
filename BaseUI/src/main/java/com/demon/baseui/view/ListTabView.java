package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/12/17
 * @email 757454343@qq.com
 * @description
 */
public class ListTabView extends ConstraintLayout {
    private LinearLayout layout;
    private String textString, textWeight;

    public ListTabView(Context context) {
        this(context, null);
    }

    public ListTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.widget_list_tab, this);
        layout = findViewById(R.id.layout_root);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ListTabView);
        textString = typedArray.getString(R.styleable.ListTabView_ltv_textString);
        textWeight = typedArray.getString(R.styleable.ListTabView_ltv_textWeight);
        int textColor = typedArray.getColor(R.styleable.ListTabView_ltv_textColor, Color.WHITE);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.ListTabView_ltv_textSize, 18);
        String[] strings1 = textString.split(",");
        String[] strings2 = textWeight.split(",");
        for (int i = 0; i < strings1.length; i++) {
            TextView textView = new TextView(context);
            float weight = Float.parseFloat(strings2[i]);
            textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, weight));
            textView.setText(strings1[i]);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setTextColor(textColor);
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);
        }
        typedArray.recycle();
    }
}
