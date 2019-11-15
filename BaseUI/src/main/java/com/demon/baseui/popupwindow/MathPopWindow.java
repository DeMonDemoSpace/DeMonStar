package com.demon.baseui.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.demon.baseui.R;

public class MathPopWindow extends PopupWindow {
    private View view;
    public MathPopWindow(View view) {
        this.view = view;
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
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
     * 显示
     */
    public void showView() {
        if (!this.isShowing()) {
            this.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
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
}
