package com.demon.baseui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author DeMon
 * @date 2018/10/12
 * @description
 */
public class ClickRightEditText extends AppCompatEditText {

    public ClickRightEditText(Context context) {
        super(context);
    }

    public ClickRightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnClickRightClickListener {
        void onClickRightClick();
    }

    private OnClickRightClickListener onClickRightListener;

    public void setOnClickRightListener(OnClickRightClickListener onClickRightListener) {
        this.onClickRightListener = onClickRightListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawableRight = getCompoundDrawables()[2];
            if (drawableRight != null) {
                //本次点击事件的x轴坐标，如果>当前控件宽度-控件右间距-drawable实际展示大小
                if (event.getX() >= (getWidth() - getPaddingRight() - drawableRight.getIntrinsicWidth())) {
                    //设置点击EditText右侧图标EditText失去焦点，
                    // 防止点击EditText右侧图标EditText获得焦点，软键盘弹出
                    setFocusableInTouchMode(false);
                    setFocusable(false);
                    if (onClickRightListener != null) {
                        onClickRightListener.onClickRightClick();
                    }
                } else {
                    setFocusableInTouchMode(true);
                    setFocusable(true);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
