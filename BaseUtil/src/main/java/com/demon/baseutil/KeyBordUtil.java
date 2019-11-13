package com.demon.baseutil;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * des:输入框弹出管理
 * Created by xsf
 * on 2016.05.13:59
 */

public class KeyBordUtil {
    public static void hideKeyBorad(Activity actvitty) {
        if (actvitty.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN) {
            if ((actvitty.getCurrentFocus() != null))
                ((InputMethodManager) actvitty.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(actvitty.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示和隐藏软键盘 View ： EditText、TextView isShow : true = show , false = hide
     *
     * @param context
     * @param view
     * @param isShow
     */
    public static void popSoftKeyboard(Context context, View view, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            view.requestFocus();
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showSoftKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * 粘贴板
     *
     * @param context
     * @param copy
     */
    public static void copy(Context context, String copy) {
        ClipboardManager cm;
        ClipData mClipData;
        //获取剪贴板管理器：
        cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        mClipData = ClipData.newPlainText("Label", copy);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showSuccessToast(context, "已复制到系统粘贴板");
    }

}
