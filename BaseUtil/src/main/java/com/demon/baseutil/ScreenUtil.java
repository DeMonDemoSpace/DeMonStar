package com.demon.baseutil;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @date 2018/1/9
 * @description 常用单位转换的辅助类
 */

public class ScreenUtil {

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpVal   dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param spVal   spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxVal   pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context 上下文
     * @param pxVal   pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }


    public static float getDensityDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;//屏幕密度DPI
    }

    public static float getDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;//屏幕密度
    }

    /**
     * 获取设备的Px宽高
     *
     * @param activity
     * @return
     */
    public static int[] getDevicePx(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return new int[]{width, height};
    }

    /**
     * 获取设备的Dp宽高
     *
     * @param activity
     * @return
     */
    public static int[] getDeviceDp(Activity activity) {
        int width = (int) (getDevicePx(activity)[0] / (getDensityDpi(activity) / 160) + 0.5);
        int height = (int) (getDevicePx(activity)[0] / (getDensityDpi(activity) / 160) + 0.5);
        return new int[]{width, height};

    }


    public static float getDP(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static float getSP(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

    public static float getPX(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, context.getResources().getDisplayMetrics());
    }
}
