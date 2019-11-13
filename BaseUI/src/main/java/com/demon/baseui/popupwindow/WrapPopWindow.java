package com.demon.baseui.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.demon.baseui.R;

public class WrapPopWindow extends PopupWindow {

    public static final int NORMAL = 0;
    public static final int MENU = 1;
    private View view;

    public WrapPopWindow(View view) {
        this(view, NORMAL);
    }

    public WrapPopWindow(View view, int what) {
        this.view = view;
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        if (what == 0) {
            view.setBackgroundResource(R.drawable.background_9);
        } else {
            view.setBackgroundResource(R.drawable.background_menu_9);
        }
        view.setPadding(15, 25, 15, 15);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener,设置其他控件变化等操作
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showViewDown(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void showView() {
        showView(view.getRootView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 显示
     */
    public void showView(View view, int direction) {
        showView(view, direction, 0, 0);
    }


    public void showView(View view, int direction, int x, int y) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(view, direction, x, y);
        } else {
            this.dismiss();
        }
    }
}
