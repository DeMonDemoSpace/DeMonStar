package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/7/13
 * @description
 */
public class BrokenLine extends View {
    private Paint mPaint;
    private int color, direction;
    private float width;
    private int mWidth, mHeight;


    public BrokenLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BrokenLine);
        color = ta.getColor(R.styleable.BrokenLine_bl_color, Color.BLACK);
        width = ta.getFloat(R.styleable.BrokenLine_bl_width, 4.0f);
        direction = ta.getInteger(R.styleable.BrokenLine_bl_direction, 0);
        ta.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(width);
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);//使画笔更加圆润
        mPaint.setStrokeCap(Paint.Cap.ROUND);//同上
        if (direction == 0) {
            canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mPaint);
        } else if (direction == 1) {
            canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
        } else if (direction == 2) {
            canvas.drawLine(mWidth / 2, mHeight / 2, mWidth, mHeight / 2, mPaint);
            canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight, mPaint);
        } else if (direction == 3) {
            canvas.drawLine(mWidth / 2, mHeight / 2, 0, mHeight / 2, mPaint);
            canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight, mPaint);
        } else if (direction == 4) {
            canvas.drawLine(mWidth / 2, mHeight / 2, mWidth, mHeight / 2, mPaint);
            canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, 0, mPaint);
        } else if (direction == 5) {
            canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, 0, mPaint);
            canvas.drawLine(mWidth / 2, mHeight / 2, 0, mHeight / 2, mPaint);
        }

    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
        invalidate();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        invalidate();
    }
}
