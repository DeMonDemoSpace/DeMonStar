package com.demon.baseui.keyboard;

import android.content.Context;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * @author DeMon
 * @date 2018/11/27
 * @email 757454343@qq.com
 * @description
 */
public class KeyboardUtil {
    // 隐藏系统键盘
    public static void hideSoftInput(EditText ed) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {// 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {// 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
        }
    }

    public static void showKeyboard(EditText ed) {
        InputMethodManager inputManager = (InputMethodManager) ed.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ed, 0);
    }

    public static void hideKeyboard(EditText ed) {
        InputMethodManager imm = (InputMethodManager) ed.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);
    }
}
