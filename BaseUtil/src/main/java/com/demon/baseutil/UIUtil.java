package com.demon.baseutil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

/**
 * @author DeMon
 * @date 2018/6/14
 * @description
 */
public class UIUtil {
    /**
     * 修改搜索框的背景
     *
     * @param search
     */
    public static void setSearchView(SearchView search, int resId) {
        if (search != null) {
            try {
                //--拿到字节码
                Class<?> argClass = search.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(search);
                //--设置背景
                mView.setBackgroundResource(resId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //获取到TextView的ID
            int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            //获取到TextView的控件
            TextView textView = search.findViewById(id);
            //设置字体大小为14sp
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);//15sp
        }
    }


    /**
     * 代码设置Margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(Context context, View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(UnitUtil.dp2px(context, l), UnitUtil.dp2px(context, t), UnitUtil.dp2px(context, r), UnitUtil.dp2px(context, b));
            v.requestLayout();
        }
    }

    /**
     * 震动的动画
     *
     * @param counts 震动几次
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0.0F, 10.0F, 0.0F, 0.0F);
        translateAnimation.setInterpolator(new CycleInterpolator((float) counts));
        translateAnimation.setDuration(500L);
        return translateAnimation;
    }

    /**
     * TextView中指定文字可点击
     *
     * @param textView
     * @param ss
     * @param start
     * @param end
     * @param listener
     */
    public static void TextClick(TextView textView, String ss, int start, int end, final TextClickListener listener) {
        if (TextUtils.isEmpty(ss)) {
            return;
        }
        SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append(ss);
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onTextClick();
                }
            }
        };
        style.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.append(style);
    }

    public interface TextClickListener {
        void onTextClick();
    }


    /**
     * 改变字体颜色
     *
     * @param tv
     * @param s
     * @param color
     * @param start
     * @param end
     */
    public static void changeTextColor(TextView tv, String s, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    /**
     * 截图全屏
     *
     * @param view
     */
    public static Bitmap captureScreen(View view) {
        View v = view.getRootView();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        bitmap.setHasAlpha(false);
        bitmap.prepareToDraw();
        return bitmap;
    }

    /**
     * 对View进行测量，布局后截图
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    public static StateListDrawable getRoundSelectorDrawable(int color) {
        Drawable pressDrawable = getAlphaRoundDrawable(color);
        Drawable normalDrawable = getRoundDrawable(color);
        return getStateDrawable(pressDrawable, normalDrawable);
    }

    //获取带透明度的圆角矩形
    public static Drawable getAlphaRoundDrawable(int color) {
        int normalColor = Color.argb(100, Color.red(color), Color.green(color), Color.blue(color));
        return getRoundDrawable(normalColor);
    }


    //根据颜色获取圆角矩形
    public static Drawable getRoundDrawable(int color) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{color, color});
        drawable.setCornerRadius(px(7));
        return drawable;
    }

    public static StateListDrawable getStateDrawable(Drawable pressDrawable, Drawable normalDrawable) {
        int pressed = android.R.attr.state_pressed;
        int select = android.R.attr.state_selected;
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{pressed}, pressDrawable);
        drawable.addState(new int[]{select}, pressDrawable);
        drawable.addState(new int[]{}, normalDrawable);
        return drawable;
    }


    public static int px(float dipValue) {
        Resources r = Resources.getSystem();
        final float scale = r.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getColor(Context context, int res) {
        Resources r = context.getResources();
        return r.getColor(res);
    }

}
