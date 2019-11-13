package com.demon.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2018/6/19
 * @description
 */
public class TitleTextView extends AppCompatTextView {
    private String title;
    private int textColor, titleColor;

    public TitleTextView(Context context) {
        this(context, null);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleTextView);
        title = typedArray.getString(R.styleable.TitleTextView_ttv_Title);
        titleColor = typedArray.getColor(R.styleable.TitleTextView_ttv_TitleColor, getResources().getColor(R.color.base_text));
        textColor = typedArray.getColor(R.styleable.TitleTextView_ttv_TextColor, getResources().getColor(R.color.base_deep_blue));
        setTitleText(getText().toString());
        //setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
        typedArray.recycle();
    }


    public void setTitleText(String text) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        SpannableString spannable1 = new SpannableString(title + "： ");
        spannable1.setSpan(new ForegroundColorSpan(titleColor), 0, title.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable1);
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        SpannableString spannable2 = new SpannableString(text);
        spannable2.setSpan(new ForegroundColorSpan(textColor), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(spannable2);
    }

    public void setTitleAndText(String title, String text) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        SpannableString spannable1 = new SpannableString(title + "： ");
        spannable1.setSpan(new ForegroundColorSpan(titleColor), 0, title.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable1);
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        SpannableString spannable2 = new SpannableString(text);
        spannable2.setSpan(new ForegroundColorSpan(textColor), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(spannable2);
    }


}
