package com.demon.baseui.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/11/26
 * @email 757454343@qq.com
 * @description
 */
public class KeyboardTEMPHelper {
    public static final int LAST = -7;
    public static final int NEXT = -8;
    public static final int ALL = -9;
    private Context context;
    private KeyboardView keyboardView;
    private EditText editText;
    private Keyboard k1;// 自定义键盘
    private KeyboardCallBack callBack;

    public KeyboardTEMPHelper(Context context, KeyboardView keyboardView) {
        this(context, keyboardView, null);
    }

    public KeyboardTEMPHelper(Context context, KeyboardView keyboardView, KeyboardCallBack callBack) {
        this.context = context;
        k1 = new Keyboard(context, R.xml.keyboard_temp);
        this.keyboardView = keyboardView;
        this.keyboardView.setOnKeyboardActionListener(listener);
        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.callBack = callBack;
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
            Editable editable = editText.getText();
            int end = editText.getSelectionEnd();
            editable.delete(0, end);
            editable.insert(0, text);
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            int end = editText.getSelectionEnd();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (editable != null && editable.length() > 0) {
                        if (start == end) {
                            editable.delete(start - 1, start);
                        } else {
                            editable.delete(start, end);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    keyboardView.setVisibility(View.GONE);
                    break;
                case ALL:
                    editText.selectAll();
                    break;
                case LAST:
                case NEXT:
                    break;
                default:
                    if (start != end) {
                        editable.delete(start, end);
                    }
                    editable.insert(start, Character.toString((char) primaryCode));
                    break;
            }
            if (callBack != null) {
                callBack.keyCall(primaryCode);
            }
        }
    };

    public void setEditText(EditText editText) {
        this.editText = editText;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏
        }
        KeyboardUtil.hideSoftInput(editText);
    }

    //Activity中获取焦点时调用，显示出键盘
    public void show() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    // 隐藏键盘
    public void hide() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }

    public boolean isVisibility() {
        if (keyboardView.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public interface KeyboardCallBack {
        void keyCall(int code);
    }

    public void setCallBack(KeyboardCallBack callBack) {
        this.callBack = callBack;
    }
}
