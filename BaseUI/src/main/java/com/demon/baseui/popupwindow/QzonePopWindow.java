package com.demon.baseui.popupwindow;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;

import com.demon.baseui.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QzonePopWindow extends PopupWindow {
    private FloatingActionButton ivDismiss;

    public QzonePopWindow(Context context, View vv) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_menu_popwindow, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = view.findViewById(R.id.frameLayout);
        frameLayout.addView(vv, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ivDismiss = view.findViewById(R.id.iv_dismiss);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivDismiss, "rotation", 0f, 135f);
        objectAnimator.setDuration(800);
        objectAnimator.start();
        ivDismiss.setOnClickListener(new View.OnClickListener() {
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
    public void showView(Activity activity) {
        if (!this.isShowing()) {
            this.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void showView(Fragment fragment) {
        if (!this.isShowing()) {
            this.showAtLocation(fragment.getView(), Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
