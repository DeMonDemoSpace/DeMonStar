package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/12/29
 * @email 757454343@qq.com
 * @description
 */
public class DrawableTextView extends AppCompatTextView {
    private Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
    private int drawableWidth;
    private int drawableHeight;
    private int drawableSize;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        drawableSize = (int) typedArray.getDimension(R.styleable.DrawableTextView_dt_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        if (drawableSize == 0) {
            drawableWidth = (int) typedArray.getDimension(R.styleable.DrawableTextView_dt_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
            drawableHeight = (int) typedArray.getDimension(R.styleable.DrawableTextView_dt_height, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
        } else {
            drawableWidth = drawableSize;
            drawableHeight = drawableSize;
        }
        drawableRight = typedArray.getDrawable(R.styleable.DrawableTextView_dt_right);
        drawableLeft = typedArray.getDrawable(R.styleable.DrawableTextView_dt_left);
        drawableTop = typedArray.getDrawable(R.styleable.DrawableTextView_dt_top);
        drawableBottom = typedArray.getDrawable(R.styleable.DrawableTextView_dt_bottom);

        if (null != drawableLeft) {
            drawableLeft.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (null != drawableRight) {
            drawableRight.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (null != drawableTop) {
            drawableTop.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (null != drawableBottom) {
            drawableBottom.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        setGravity(Gravity.CENTER);
        typedArray.recycle();
    }


    public void setDrawableWidth(int drawableWidth) {
        this.drawableWidth = drawableWidth;
    }


    public void setDrawableHeight(int drawableHeight) {
        this.drawableHeight = drawableHeight;
    }

    public void setDrawableSize(int size) {
        this.drawableHeight = size;
        this.drawableWidth = size;
    }

    public void setDrawableTop(int id) {
        drawableTop = getResources().getDrawable(id);
        drawableTop.setBounds(0, 0, drawableWidth, drawableHeight);
        setCompoundDrawables(null, drawableTop, null, null);
    }

    public void setDrawableBottom(int id) {
        drawableBottom = getResources().getDrawable(id);
        drawableBottom.setBounds(0, 0, drawableWidth, drawableHeight);
        setCompoundDrawables(null, null, null, drawableBottom);
    }

    public void setDrawableLeft(int id) {
        drawableLeft = getResources().getDrawable(id);
        drawableLeft.setBounds(0, 0, drawableWidth, drawableHeight);
        setCompoundDrawables(drawableLeft, null, null, null);
    }

    public void setDrawableRight(int id) {
        drawableRight = getResources().getDrawable(id);
        drawableRight.setBounds(0, 0, drawableWidth, drawableHeight);
        setCompoundDrawables(null, null, drawableRight, null);
    }
}
